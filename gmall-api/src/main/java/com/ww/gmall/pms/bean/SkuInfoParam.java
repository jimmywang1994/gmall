package com.ww.gmall.pms.bean;

import lombok.Data;

import java.util.List;

@Data
public class SkuInfoParam {
    private String catalog3Id;
    private List<SkuAttrValue> skuAttrValueList;
    private String keyword;
}
