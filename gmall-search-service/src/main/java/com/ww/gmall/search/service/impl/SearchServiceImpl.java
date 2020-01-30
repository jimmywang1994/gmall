package com.ww.gmall.search.service.impl;

import com.ww.gmall.pms.bean.SearchSkuInfo;
import com.ww.gmall.pms.bean.SkuAttrValue;
import com.ww.gmall.pms.bean.SkuInfoParam;
import com.ww.gmall.pms.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    JestClient jestClient;

    @Override
    public List<SearchSkuInfo> list(SkuInfoParam skuInfoParam) {
        String dslStr = getDslString(skuInfoParam);
        Search search = new Search.Builder(dslStr).addIndex("gmall").addType("SkuInfo").build();
        List<SearchSkuInfo> searchSkuInfoList = new ArrayList<>();
        SearchResult result = null;
        try {
            result = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<SearchResult.Hit<SearchSkuInfo, Void>> hits = result.getHits(SearchSkuInfo.class);
        for (SearchResult.Hit<SearchSkuInfo, Void> hit : hits) {
            SearchSkuInfo searchSkuInfo = hit.source;
            searchSkuInfoList.add(searchSkuInfo);
        }
        return null;
    }

    private String getDslString(SkuInfoParam skuInfoParam) {
        //筛选条件列表
        List<SkuAttrValue> skuAttrValueList = skuInfoParam.getSkuAttrValueList();
        //搜索关键字
        String keyword = skuInfoParam.getKeyword();
        //三级分类id
        String catalog3Id = skuInfoParam.getCatalog3Id();
        //jest的dsl工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //filter
        if (skuAttrValueList != null) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", skuAttrValue.getValueId());
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        //must
        if (StringUtils.isNotBlank(keyword)) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("catalog3Id", catalog3Id);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);
        return searchSourceBuilder.toString();
    }
}
