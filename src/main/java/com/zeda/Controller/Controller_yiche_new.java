package com.zeda.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zeda.Dao.DaoFather;
import com.zeda.Entity.Bean_brand;
import com.zeda.Until.HttpUntil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller_yiche_new {

    private HttpUntil httpUntil = new HttpUntil();

    public boolean Method_1_Brand() {
        String status;
        String content = "";
        do {
            content = httpUntil.Method_RequestAPI("https://mapi.yiche.com/web_api/car_model_api/api/v1/master/get_master_list?", "{}");
            while (content.equals("Error")) {
                content = httpUntil.Method_RequestAPI("https://mapi.yiche.com/web_api/car_model_api/api/v1/master/get_master_list?", "{}");
                System.out.println("进入循环");
            }
            status = JSONObject.parseObject(content).getString("status");
        } while (!status.equals("1"));
        DaoFather dao_brand = new DaoFather(0, 0);

        System.out.println(content);
        JSONObject jsonObject = JSONObject.parseObject(content);
        JSONArray BrandList = jsonObject.getJSONArray("data");

        for (int i = 0; i < BrandList.size(); i++) {
            JSONObject oneBrand = BrandList.getJSONObject(i);
            Bean_brand bean_brand = new Bean_brand();
            bean_brand.setC_brandList(oneBrand.getString("brandList"));
            bean_brand.setC_advertFlag(oneBrand.getString("advertFlag"));
            bean_brand.setC_advertInfo(oneBrand.getString("advertInfo"));
            bean_brand.setC_brand_id(oneBrand.getString("id"));
            bean_brand.setC_name(oneBrand.getString("name"));
            bean_brand.setC_logoUrl(oneBrand.getString("logoUrl"));
            bean_brand.setC_logoUrlWp(oneBrand.getString("logoUrlWp"));
            bean_brand.setC_initial(oneBrand.getString("initial"));
            bean_brand.setC_logoStory(oneBrand.getString("logoStory"));
            bean_brand.setC_allSpell(oneBrand.getString("allSpell"));
            bean_brand.setC_saleStatus(oneBrand.getString("saleStatus"));
            bean_brand.setC_yiCheHuiTag(oneBrand.getString("yiCheHuiTag"));
            bean_brand.setC_photoCount(oneBrand.getString("photoCount"));
            bean_brand.setC_DownState("否");
            bean_brand.setC_DownTime("--");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String time = df.format(new Date());
            bean_brand.setC_updateTime(time);
            bean_brand.setC_updateremark(time+"入库");
            dao_brand.MethodInsert(bean_brand);
        }
        System.out.println("Method_2 ---->品牌完成入库");

        return true;
    }

}
