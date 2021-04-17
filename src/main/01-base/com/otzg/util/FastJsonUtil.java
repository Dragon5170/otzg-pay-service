package com.otzg.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * @Author G.
 * @Date 2019/11/23 0023 下午 3:10
 */
public final class FastJsonUtil {

    public static Map getJson(Object o){
        return JSON.parseObject(o.toString());
    }

    public static JSONArray getJsonArray(Object o){
        return JSON.parseArray(o.toString());
    }
    /**
     * 遍历json树
     * @author G/2018年1月15日
     * @param jo
     * @param id
     * @return
     */
    public static JSONObject findById(JSONObject jo, String id) {
        JSONObject resultJo = new JSONObject();
        if(null != jo.get("id") && id.equals(jo.getString("id"))){
            resultJo = jo;
        }else if(null != jo.get("children") && !jo.getJSONArray("children").isEmpty()){
            resultJo = findById(jo.getJSONArray("children"),id);
        }
        return resultJo;
    }

    /**
     * 遍历jsonArray树
     * @author G/2018年1月15日
     * @param ja
     * @param id
     * @return
     */
    public static JSONObject findById(JSONArray ja, String id) {
        JSONObject resultJo = new JSONObject();
        for(int i = 0; i < ja.size(); i++) {
            resultJo = findById(ja.getJSONObject(i),id);
            if(resultJo.isEmpty()){
                continue;
            }else {
                break;
            }
        }
        return resultJo;
    }

    /**
     * json对象加上check
     * @author G/2018年1月16日
     * @param jo  json对象
     * @param ids id集合
     * @return
     */
    public static JSONObject addChecked(JSONObject jo,String ids) {
        if(null != jo.get("id")&& FuncUtil.CountSubStr(jo.getString("id"),ids)>0){
            jo.put("checked",true);
        }

        if(null != jo.get("children") && !jo.getJSONArray("children").isEmpty()){
            jo.put("children",addChecked(jo.getJSONArray("children"),ids));
        }
        return jo;
    }

    /**
     * json 数组加上 checked
     * @author G/2018年1月16日
     * @param ja json数组
     * @param ids id集合
     * @return
     */
    public static JSONArray addChecked(JSONArray ja,String ids) {
        JSONArray reja=new JSONArray();
        for(int i = 0; i < ja.size(); i++) {
            reja.add(addChecked(ja.getJSONObject(i),ids));
        }
        return reja;
    }

    /**
     * 去掉json中="null" 的项目
     * @author G/2018/6/21 14:48
     * @param jo
     */
    public final static JSONObject getClean(JSONObject jo){
        JSONObject rejo = new JSONObject();
        Iterator iterator = jo.keySet().iterator();
        while(iterator.hasNext()){
            String key =(String) iterator.next();
            String value=jo.getString(key);
            if(!value.equals("null")){
                rejo.put(key,value);
            }
        }
        return rejo;
    }

//	public static void main(String args[]) {
//		String a="{\"id\":1157,\"name\":\"首页\",\"icon\":\"\",\"href\":\"state-ic\",\"spread\":false,\"sort\":1,\"pid\":0,\"children\":[{\"id\":1,\"name\":\"首页\",\"icon\":\"\",\"href\":\"state\",\"spread\":false,\"pid\":0,\"children\":[],\"checked\":true},{\"id\":57,\"name\":\"首页\",\"icon\":\"\",\"href\":\"state-ic\",\"spread\":false,\"sort\":1,\"pid\":0,\"children\":[],\"checked\":true},{\"id\":58,\"name\":\"产品\",\"icon\":\"\",\"href\":\"pro-ic\",\"spread\":false,\"sort\":2,\"pid\":0,\"children\":[{\"id\":67,\"name\":\"产品管理\",\"icon\":\"\",\"href\":\"ftgreen\",\"spread\":false,\"sort\":1,\"pid\":58,\"children\":[{\"id\":69,\"name\":\"产品\",\"icon\":\"\",\"href\":\"/admin/product!list.do\",\"spread\":false,\"sort\":1,\"pid\":67,\"checked\":true},{\"id\":93,\"name\":\"产品分类\",\"icon\":\"\",\"href\":\"/admin/category!index.do\",\"spread\":false,\"sort\":2,\"pid\":67,\"checked\":true}],\"checked\":true},{\"id\":68,\"name\":\"库存管理\",\"icon\":\"\",\"href\":\"ftgreen\",\"spread\":false,\"sort\":2,\"pid\":58,\"children\":[{\"id\":70,\"name\":\"库存\",\"icon\":\"\",\"href\":\"/admin/warehouse!list.do\",\"spread\":false,\"sort\":1,\"pid\":68}]}],\"checked\":true},{\"id\":59,\"name\":\"订单\",\"icon\":\"\",\"href\":\"orders-ic\",\"spread\":false,\"sort\":3,\"pid\":0,\"children\":[{\"id\":81,\"name\":\"订单管理\",\"icon\":\"\",\"href\":\"ftgreen\",\"spread\":false,\"sort\":1,\"pid\":59,\"children\":[{\"id\":82,\"name\":\"订单列表\",\"icon\":\"\",\"href\":\"/admin/orders!list.do\",\"spread\":false,\"sort\":1,\"pid\":81},{\"id\":111,\"name\":\"下属订单\",\"icon\":\"\",\"href\":\"/admin/orders!listMy.do\",\"spread\":false,\"sort\":1,\"pid\":81}]}]},{\"id\":60,\"name\":\"客户\",\"icon\":\"\",\"href\":\"customer-ic\",\"spread\":false,\"sort\":6,\"pid\":0,\"children\":[{\"id\":73,\"name\":\"客户管理\",\"icon\":\"\",\"href\":\"ftgreen\",\"spread\":false,\"sort\":1,\"pid\":60,\"children\":[{\"id\":75,\"name\":\"会员\",\"icon\":\"\",\"href\":\"/admin/member!list.do\",\"spread\":false,\"sort\":1,\"pid\":73},{\"id\":114,\"name\":\"下属会员\",\"icon\":\"\",\"href\":\"/admin/member!listMy.do\",\"spread\":false,\"sort\":1,\"pid\":73}]}]},{\"id\":61,\"name\":\"资讯\",\"icon\":\"\",\"href\":\"info-ic\",\"spread\":false,\"sort\":8,\"pid\":0,\"children\":[{\"id\":83,\"name\":\"资讯管理\",\"icon\":\"\",\"href\":\"ftgreen\",\"spread\":false,\"sort\":1,\"pid\":61,\"children\":[{\"id\":84,\"name\":\"资讯\",\"icon\":\"\",\"href\":\"/admin/news!list.do\",\"spread\":false,\"sort\":1,\"pid\":83},{\"id\":85,\"name\":\"分类\",\"icon\":\"\",\"href\":\"/admin/newsCategory!list.do\",\"spread\":false,\"sort\":2,\"pid\":83}]}]},{\"id\":62,\"name\":\"图片\",\"icon\":\"\",\"href\":\"imgs-ic\",\"spread\":false,\"sort\":5,\"pid\":0,\"children\":[{\"id\":71,\"name\":\"图片空间\",\"icon\":\"\",\"href\":\"ftgreen\",\"spread\":false,\"sort\":1,\"pid\":62,\"children\":[{\"id\":72,\"name\":\"全部图片\",\"icon\":\"\",\"href\":\"/admin/imageSpace!space.do\",\"spread\":false,\"sort\":1,\"pid\":71}]}]},{\"id\":63,\"name\":\"推广\",\"icon\":\"\",\"href\":\"promotion-ic\",\"spread\":false,\"sort\":7,\"pid\":0,\"children\":[{\"id\":94,\"name\":\"业务推广\",\"icon\":\"\",\"href\":\"ftgreen\",\"spread\":false,\"pid\":63,\"children\":[]}]},{\"id\":64,\"name\":\"统计\",\"icon\":\"\",\"href\":\"data-ic\",\"spread\":false,\"sort\":4,\"pid\":0,\"children\":[{\"id\":86,\"name\":\"销售统计\",\"icon\":\"\",\"href\":\"ftgreen\",\"spread\":false,\"sort\":1,\"pid\":64,\"children\":[{\"id\":87,\"name\":\"区域销售统计\",\"icon\":\"\",\"href\":\"/admin/statistics!salesAreaList.do\",\"spread\":false,\"sort\":3,\"pid\":86},{\"id\":112,\"name\":\"我的销售统计\",\"icon\":\"\",\"href\":\"/admin/statistics!salesMyList.do\",\"spread\":false,\"sort\":1,\"pid\":86},{\"id\":113,\"name\":\"业务销售统计\",\"icon\":\"\",\"href\":\"/admin/statistics!salesList.do\",\"spread\":false,\"sort\":2,\"pid\":86},{\"id\":116,\"name\":\"客户销售统计\",\"icon\":\"\",\"href\":\"/admin/statistics!salesUnitList.do\",\"spread\":false,\"sort\":3,\"pid\":86},{\"id\":117,\"name\":\"刷新本月统计\",\"icon\":\"\",\"href\":\"/admin/orders!calcSalesStatistic.do\",\"spread\":false,\"sort\":9,\"pid\":86}]}]},{\"id\":65,\"name\":\"广告\",\"icon\":\"\",\"href\":\"ad-ic\",\"spread\":false,\"sort\":9,\"pid\":0,\"children\":[{\"id\":90,\"name\":\"广告管理\",\"icon\":\"\",\"href\":\"ftgreen\",\"spread\":false,\"sort\":1,\"pid\":65,\"children\":[{\"id\":91,\"name\":\"广告位\",\"icon\":\"\",\"href\":\"adver!findAdverPlace.do\",\"spread\":false,\"sort\":2,\"pid\":90},{\"id\":92,\"name\":\"广告\",\"icon\":\"\",\"href\":\"adver!findAdver.do\",\"spread\":false,\"sort\":1,\"pid\":90}]}]},{\"id\":66,\"name\":\"设置\",\"icon\":\"\",\"href\":\"set-ic\",\"spread\":false,\"sort\":10,\"pid\":0,\"children\":[{\"id\":76,\"name\":\"管理员设置\",\"icon\":\"\",\"href\":\"ftgreen\",\"spread\":false,\"sort\":1,\"pid\":66,\"children\":[{\"id\":77,\"name\":\"群组\",\"icon\":\"\",\"href\":\"/admin/role!list.do\",\"spread\":false,\"sort\":2,\"pid\":76},{\"id\":78,\"name\":\"资源\",\"icon\":\"\",\"href\":\"/admin/resc!list.do\",\"spread\":false,\"sort\":3,\"pid\":76},{\"id\":79,\"name\":\"管理员\",\"icon\":\"\",\"href\":\"/admin/user!list.do\",\"spread\":false,\"sort\":1,\"pid\":76},{\"id\":80,\"name\":\"修改密码\",\"icon\":\"\",\"href\":\"user!getUpPwdPage.do\",\"spread\":false,\"sort\":4,\"pid\":76},{\"id\":89,\"name\":\"网站首页设置\",\"icon\":\"\",\"href\":\"/admin/sysconf!indexSet.do\",\"spread\":false,\"sort\":5,\"pid\":76},{\"id\":108,\"name\":\"发货地址\",\"icon\":\"\",\"href\":\"/admin/address!add.do\",\"spread\":false,\"sort\":6,\"pid\":76},{\"id\":109,\"name\":\"访问记录\",\"icon\":\"\",\"href\":\"/admin/optionLogs!findOptionLogs.do?pageSize=1000\",\"spread\":false,\"sort\":7,\"pid\":76},{\"id\":115,\"name\":\"支付记录\",\"icon\":\"\",\"href\":\"/admin/payOrders!account.do\",\"spread\":false,\"sort\":9,\"pid\":76}]}]}],\"checked\":true}";
//
//		JSONObject j = JSONObject.fromObject(a);
//		LogUtil.print(j.toString());
//		JSONObject jo = findById(j,"69");
//		LogUtil.print(jo.toString());
//	}
}

