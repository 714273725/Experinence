package com.jack.myexperience.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/2/24 0024.
 */
public class CityBean {
    /**
     * province : 北京
     * city : [{"cityname":"北京","code":"101010100"},{"cityname":"朝阳","code":"101010300"},{"cityname":"顺义","code":"101010400"},{"cityname":"怀柔","code":"101010500"},{"cityname":"通州","code":"101010600"},{"cityname":"昌平","code":"101010700"},{"cityname":"延庆","code":"101010800"},{"cityname":"丰台","code":"101010900"},{"cityname":"石景山","code":"101011000"},{"cityname":"大兴","code":"101011100"},{"cityname":"房山","code":"101011200"},{"cityname":"密云","code":"101011300"},{"cityname":"门头沟","code":"101011400"},{"cityname":"平谷","code":"101011500"},{"cityname":"八达岭","code":"101011600"},{"cityname":"佛爷顶","code":"101011700"},{"cityname":"汤河口","code":"101011800"},{"cityname":"密云上甸子","code":"101011900"},{"cityname":"斋堂","code":"101012000"},{"cityname":"霞云岭","code":"101012100"},{"cityname":"北京城区","code":"101012200"},{"cityname":"海淀","code":"101010200"}]
     */


    private List<Province> cityCode;

    public void setCityCode(List<Province> cityCode) {
        this.cityCode = cityCode;
    }

    public List<Province> getProvinceList() {
        return cityCode;
    }

    public static class Province {
        private String province;
        /**
         * cityname : 北京
         * code : 101010100
         */

        private List<City> city;
        public void setProvince(String province) {
            this.province = province;
        }
        public void setCity(List<City> city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public List<City> getCity() {
            return city;
        }

        public static class City {
            private String cityname;
            private String code;

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCityname() {
                return cityname;
            }
            public String getCode() {
                return code;
            }
        }
    }
}
