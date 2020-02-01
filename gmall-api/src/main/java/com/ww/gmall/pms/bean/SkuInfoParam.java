package com.ww.gmall.pms.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SkuInfoParam implements Serializable {
    private long catalog3Id;
    private List<SkuAttrValue> skuAttrValueList;
    private String keyword;
}
