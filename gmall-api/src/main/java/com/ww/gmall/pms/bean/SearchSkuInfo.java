package com.ww.gmall.pms.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SearchSkuInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private String skuName;
    private String skuDesc;
    private String catalog3Id;
    private Double price;
    private String skuDefaultImg;
    private Double hotScore;
    private String productId;
    private List<SkuAttrValue> skuAttrValueList;

}
