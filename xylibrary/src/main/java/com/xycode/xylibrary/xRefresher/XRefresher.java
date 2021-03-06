package com.xycode.xylibrary.xRefresher;

/**
 * Created by XY on 2016/6/18.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.xycode.xylibrary.R;
import com.xycode.xylibrary.adapter.XAdapter;
import com.xycode.xylibrary.okHttp.OkHttp;
import com.xycode.xylibrary.okHttp.Param;
import com.xycode.xylibrary.uiKit.recyclerview.HorizontalDividerItemDecoration;
import com.xycode.xylibrary.utils.L;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by XY on 2016/6/17.
 */
public class XRefresher<T> extends CoordinatorLayout {

    public static final int LOADER_MORE = 0;
    public static final int LOADER_LOADING = 1;
    public static final int LOADER_NO_MORE = 2;

    private int loadMoreState = LOADER_NO_MORE;

    private static final int REFRESH = 1;
    private static final int LOAD = 2;
    private static String PAGE = "pagerNumber";
    private static String PAGE_SIZE = "pageSize";

    //    private static XAdapter.ICustomerLoadMore iCustomerLoadMore;
//    private static int loaderLayout = R.layout.layout_blank;
    private static Dialog loadingDialog;
    private static int[] loadingColorRes = null;

    private LoadMoreView loadMoreView;
    private int background;
    private boolean backgroundIsRes = false;
    private int backgroundNoData;
    private boolean backgroundNoDataIsRes = false;

    private int hintColor;
    private float hintSize;
    private String hint;

    private Activity activity;

    private RefreshState state;
    private XAdapter<T> adapter;

    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;
    private TextView textView;

    private RefreshRequest refreshRequest;

    private int lastVisibleItem = 0;
    private boolean loadMore;
    private CoordinatorLayout rlMain;

    public static void setCustomerLoadMoreView(@LayoutRes int footerLayout) {
//        XRefresher.loaderLayout = footerLayout;
        LoadMoreView.setlayoutId(footerLayout);
    }

    public XRefresher(Context context) {
        super(context, null);
    }

    public XRefresher(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_refresher, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XRefresher);

        hint = typedArray.getString(R.styleable.XRefresher_hint);
        hintSize = typedArray.getDimensionPixelSize(R.styleable.XRefresher_hintSize, 1);
        hintColor = typedArray.getColor(R.styleable.XRefresher_hintColor, 1);
        background = typedArray.getColor(R.styleable.XRefresher_bg, 1);
        if (background == 1) {
            background = typedArray.getResourceId(R.styleable.XRefresher_bg, 1);
            backgroundIsRes = background != 1;
        }
        backgroundNoData = typedArray.getColor(R.styleable.XRefresher_bgNoData, 1);
        if (backgroundNoData == 1) {
            backgroundNoData = typedArray.getResourceId(R.styleable.XRefresher_bgNoData, 1);
            backgroundNoDataIsRes = backgroundNoData != 1;
        }

        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rlMain = (CoordinatorLayout) findViewById(R.id.rlMain);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        recyclerView = (RecyclerView) findViewById(R.id.rvMain);
        loadMoreView = (LoadMoreView) findViewById(R.id.loadMoreView);
        textView = (TextView) findViewById(R.id.tvMain);

        textView.setText(hint);
        if (hintSize != 1) textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintSize);
        if (hintColor != 1) textView.setTextColor(hintColor);
        if (backgroundIsRes) {
            rlMain.setBackgroundResource(background);
        } else if (background != 1) {
            rlMain.setBackgroundColor(background);
        }
        if (backgroundNoDataIsRes) {
            textView.setBackgroundResource(backgroundNoData);
        } else if (backgroundNoData != 1) {
            textView.setBackgroundColor(backgroundNoData);
        }

    }

    /**
     * @param activity
     * @param adapter
     * @param loadMore
     * @param swipeListener
     * @param refreshRequest
     */
    public void setup(Activity activity, XAdapter<T> adapter, boolean loadMore, OnSwipeListener swipeListener, RefreshRequest refreshRequest) {
        init(activity, adapter, loadMore, swipeListener, refreshRequest, 10);
    }

    /**
     * @param activity
     * @param adapter
     * @param loadMore
     * @param swipeListener
     * @param refreshRequest
     * @param refreshPageSize
     */
    public void setup(Activity activity, XAdapter<T> adapter, boolean loadMore, OnSwipeListener swipeListener, RefreshRequest refreshRequest, int refreshPageSize) {
        init(activity, adapter, loadMore, swipeListener, refreshRequest, refreshPageSize);
    }

    public void setup(Activity activity, XAdapter<T> adapter, boolean loadMore, @NonNull RefreshRequest refreshRequest) {
        init(activity, adapter, loadMore, null, refreshRequest, 10);
    }

    public void setup(Activity activity, XAdapter<T> adapter, boolean loadMore, @NonNull RefreshRequest refreshRequest, int refreshPageSize) {
        init(activity, adapter, loadMore, null, refreshRequest, refreshPageSize);
    }

    private void init(Activity activity, XAdapter<T> adapter, boolean loadMore, final OnSwipeListener swipeListener, final RefreshRequest refreshRequest, int refreshPageSize) {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        this.loadMore = loadMore;
        this.activity = activity;
        this.refreshRequest = refreshRequest;
        this.state = new RefreshState(refreshPageSize);
        this.adapter = adapter;
        this.recyclerView.setAdapter(adapter);
        ((SwipeRefreshLayout) findViewById(R.id.swipe)).setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeListener != null) swipeListener.onRefresh();
                if (refreshRequest != null) refreshList();
            }
        });
        if (loadMore) {
            setLoadMoreListener();
        }
        if (loadingColorRes != null) {
            swipe.setColorSchemeResources(loadingColorRes);
        }
    }

    private void setLoadMoreListener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean swipeMore = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                L.e("lastVisibleItem--- " + lastVisibleItem);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && swipeMore && lastVisibleItem + 2 >= getAdapter().getItemCount()) {
                    if (!state.lastPage && loadMoreState == LOADER_MORE) {
                        setLoadMoreState(LOADER_LOADING);
                        getDataByRefresh(state.pageIndex + 1, state.pageDefaultSize);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                swipeMore = dy > 0;
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
    }

    /**
     * @param pageSize page size shown in one time
     */
    private void getDataByRefresh(int pageSize) {
        getDataByRefresh(1, pageSize, REFRESH);
    }

    private void getDataByRefresh(int page, int pageSize) {
        getDataByRefresh(page, pageSize, LOAD);
    }

    private void getDataByRefresh(final int page, final int pageSize, final int refreshType) {
        Param params = new Param();
        params.put(PAGE, refreshType == REFRESH ? "1" : String.valueOf(page));
        //params.put(PAGE_SIZE, String.valueOf(pageSize));
        String url = refreshRequest.setRequestParamsReturnUrl(params);
        OkHttp.getInstance().postForm(url, OkHttp.setFormBody(params, false), new OkHttp.OkResponseListener() {
            @Override
            public void handleJsonSuccess(Call call, Response response, JSONObject json) {
                if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
                final List<T> newList = refreshRequest.setListData(json);
                if (newList.size() < pageSize) state.setLastPage(true);
                final List<T> list = new ArrayList<>();

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (refreshType) {
                            case REFRESH:
                                swipe.setRefreshing(false);
                                if (state.pageIndex == 0) state.pageIndex++;
                                break;
                            default:
                                list.addAll(getAdapter().getDataList());
                                state.pageIndex++;
                                break;
                        }
                        setLoadMoreState(state.lastPage ? LOADER_NO_MORE : LOADER_MORE);
                        if (newList.size() > 0) {
                            for (T newItem : newList) {
                                boolean hasSameItem = false;
                                for (T listItem : list) {
                                    if (refreshRequest.ignoreSameItem(newItem, listItem)) {
                                        hasSameItem = true;
                                        break;
                                    }
                                }
                                if (!hasSameItem) list.add(newItem);
                            }
                            Collections.sort(list, new Comparator<T>() {
                                public int compare(T arg0, T arg1) {
                                    return refreshRequest.compareTo(arg0, arg1);
                                }
                            });
                            getAdapter().setDataList(list);
                        }
                        textView.setVisibility(getAdapter().getDataList().size() == 0 ? VISIBLE : GONE);
                    }
                });
            }

            @Override
            public void handleJsonError(Call call, Response response, JSONObject json) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loadingDialog != null && loadingDialog.isShowing())
                            loadingDialog.dismiss();
                        switch (refreshType) {
                            case REFRESH:
                                swipe.setRefreshing(false);
                                break;
                            case LOAD:
                                setLoadMoreState(state.lastPage ? LOADER_NO_MORE : LOADER_MORE);
                                break;
                        }
                    }
                });
            }

            @Override
            protected void handleNoServerNetwork(Call call, boolean isCanceled) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loadingDialog != null && loadingDialog.isShowing())
                            loadingDialog.dismiss();
                        switch (refreshType) {
                            case REFRESH:
                                swipe.setRefreshing(false);
                                break;
                            case LOAD:
                                setLoadMoreState(state.lastPage ? LOADER_NO_MORE : LOADER_MORE);
                                break;
                        }
                    }
                });
            }

        });
    }

    public void setRefreshing(boolean refreshing) {
        swipe.setRefreshing(refreshing);
    }

    public void setNoDataHint(String hint) {
        textView.setText(hint);
    }

    /**
     * refresh list
     */
    public void refreshList() {
        refreshList(false);
    }

    public void refreshList(boolean showDialog) {
        if (showDialog && showDialog) {
            if (loadingDialog != null)
                loadingDialog.show();
        }
        if (getAdapter().getDataList().size() > 0) {
            getDataByRefresh(getAdapter().getDataList().size());
        } else {
            getDataByRefresh(state.pageDefaultSize);
            swipe.setRefreshing(false);
        }
    }

    public XAdapter<T> getAdapter() {
        return adapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipe;
    }

    public void setRecyclerViewDivider(@ColorRes int dividerColor, @DimenRes int dividerHeight, @DimenRes int marginLeft, @DimenRes int marginRight) {
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(activity)
                .colorResId(dividerColor).sizeResId(dividerHeight)
                .marginResId(marginLeft, marginRight)
                .build()
        );
    }

    public static void setLoadingDialog(Dialog loadingDialog) {
        XRefresher.loadingDialog = loadingDialog;
    }

    public static void setLoadingArrowColor(@ColorRes int... loadingColorRes) {
        XRefresher.loadingColorRes = new int[loadingColorRes.length];
        for (int i = 0; i < loadingColorRes.length; i++) {
            XRefresher.loadingColorRes[i] = loadingColorRes[i];
        }
    }

    public static void resetPageParamsNames(String page, String pageSize) {
        XRefresher.PAGE = page;
        XRefresher.PAGE_SIZE = pageSize;
    }

    private void setLoadMoreState(int loadMoreState) {
        this.loadMoreState = loadMoreState;
        switch (loadMoreState) {
            case LOADER_LOADING:
                loadMoreView.show();
                break;
            default:
                loadMoreView.hide();
                break;
        }
    }

    public static abstract class RefreshRequest<T> implements IRefreshRequest<T> {

        /**
         * ignore the same item in the list，use return newItem.getId().equals(listItem.getId());
         * if not,  don't override it;
         *
         * @param newItem
         * @param listItem
         * @return
         */
        protected boolean ignoreSameItem(T newItem, T listItem) {
            return false;
        }

        /**
         * reorder the list，returns:
         * -1 large to small
         * 1 small to large
         * 0 same
         * eg: long Long.compareTo(), item0:item1,default result is 1;
         * if no use, don't override if.
         *
         * @param item0
         * @param item1
         * @return
         */
        protected int compareTo(T item0, T item1) {
            return 0;
        }
    }

    private interface IRefreshRequest<T> {
        /**
         * return the url you need to post, and set the params in the method;
         *
         * @param params
         * @return
         */
        String setRequestParamsReturnUrl(Param params);

        /**
         * handle the JSON and get the List from the json, then return it.
         *
         * @param json
         * @return
         */
        List<T> setListData(JSONObject json);
    }

    public interface OnSwipeListener {
        /**
         * Refresh
         */
        void onRefresh();
    }

    public static class RefreshState implements Serializable {
        boolean lastPage = false;
        int pageIndex = 0;
        int pageDefaultSize = 10;

        public RefreshState(int pageDefaultSize) {
            this.pageDefaultSize = pageDefaultSize;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }
    }
}
