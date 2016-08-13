package ye.jian.ge.utils;

import android.content.Context;
import android.widget.Toast;

import ye.jian.ge.BaseContext;


/**
 * �Toast
 * @author Administrator
 *
 */
public class ToastUtils {

	protected static Toast toast   = null;
	private static String oldMsg;
	private static long oneTime = 0;
	private static long twoTime = 0;
	public static void showToast(int resId) {
		showToast(BaseContext.getInstance().getString(resId));
	}
	public static void showToast( String s) {
		if (toast == null) {
			toast = Toast.makeText(BaseContext.getInstance(), s, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s!=null&&s.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oneTime = twoTime;
	}
	public static void showToast(Context context, String s){      
		if(toast==null){   
			toast =Toast.makeText(context, s, Toast.LENGTH_SHORT);  
			toast.show();  
			oneTime=System.currentTimeMillis();  
		}else{  
			twoTime=System.currentTimeMillis();  
			if(s.equals(oldMsg)){  
				if(twoTime-oneTime>Toast.LENGTH_SHORT){  
					toast.show();  
				}  
			}else{  
				oldMsg = s;  
				toast.setText(s);  
				toast.show();  
			}         
		}  
		oneTime=twoTime;  
	}  

	public static void showToast(Context context, int resId){     
		showToast(context, context.getString(resId));  
	}

	/**
	 * 显示支付完成后的 支付结果
	 *
	 * @param context
	 * @param isSuccess
	 */
	/*public static void showPayResult(Context context, boolean isSuccess) {
		View view = LayoutInflater.from(context).inflate(R.layout.layout_pay_toast, null);
		TextView tv = (TextView) view.findViewById(R.id.tv_tips);
		tv.setText(context.getString(isSuccess ? R.string.text_pay_success : R.string.text_pay_fail));
		if (isSuccess) {
			//设置提示图片
		}
		Toast toast = new Toast(context);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}*/
}
