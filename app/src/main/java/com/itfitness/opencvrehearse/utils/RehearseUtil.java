package com.itfitness.opencvrehearse.utils;

import java.util.ArrayList;
import java.util.List;

public class RehearseUtil {
    /**
     * 获取首页的案例列表
     * @return
     */
    public static List<String> getRehearseList(){
        ArrayList<String> rehearseList = new ArrayList<>();
        rehearseList.add("人像美颜");
        rehearseList.add("图形验证码提取");
        rehearseList.add("浮雕效果");
        rehearseList.add("简笔画");
        rehearseList.add("图像映像");
        return rehearseList;
    }
}
