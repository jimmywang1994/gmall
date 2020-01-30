package com.ww.gmall.pms.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchSkuInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    private String skuName;
    private String skuDesc;
    private String catalog3Id;
    private double price;
    private String defaultImg;
    private double hotScore;
    private String productId;
    private List<SkuAttrValue> skuAttrValueList;

}
