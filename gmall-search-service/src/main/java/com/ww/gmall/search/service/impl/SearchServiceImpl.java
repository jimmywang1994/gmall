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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    JestClient jestClient;

    @Override
    public List<SearchSkuInfo> list(String catalog3Id,
                                    String keyword, String[] valueIds) {
        String dslStr = getDslString(catalog3Id, keyword, valueIds);
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
            Map<String, List<String>> highlight = hit.highlight;
            if (highlight != null) {
                String skuName = highlight.get("skuName").get(0);
                searchSkuInfo.setSkuName(skuName);
            }
            searchSkuInfoList.add(searchSkuInfo);
        }
        return searchSkuInfoList;
    }

    private String getDslString(String catalog3Id,
                                String keyword, String[] valueIds) {
        //筛选条件列表
        //List<SkuAttrValue> skuAttrValueList = skuInfoParam.getSkuAttrValueList();
        //搜索关键字

        //jest的dsl工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //filter
        if (valueIds != null) {
            for (String valueId : valueIds) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", valueId);
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
        //aggs聚合查询
//        TermsAggregationBuilder groupby_attr = AggregationBuilders.terms("groupby_attr").field("skuAttrValueList.valueId");
//        searchSourceBuilder.aggregation(groupby_attr);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);
        searchSourceBuilder.sort("id", SortOrder.DESC);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        return searchSourceBuilder.toString();
    }
}
