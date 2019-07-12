<#include "module/default.ftl">
<@default title="${options.blog_title}-归档" keywords="${options.seo_keywords?if_exists}" description="${options.seo_desc?if_exists}">
<header class="intro-header" style="background-image: url('${options.hux_general_tags_cover?default("/${themeName}/source/img/tag-bg.jpg")}"')">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                <div class="site-heading" id="tag-heading">
                    <h1>Archives</h1>
                    <span class="subheading">${options.hux_general_tags_slogn?if_exists}</span>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- Main Content -->
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
            <div class="archive animated fadeInDown">
                <ul class="list-with-title">
                    <@articleTag method="archivesLess">
                        <#list archivesLess as archive>
                            <div class="listing-title">${archive.year}</div>
                            <ul class="listing">
                                <#list archive.posts?sort_by("postDate")?reverse as post>
                                    <div class="listing-item">
                                        <div class="listing-post">
                                            <a href="/archives/${post.postUrl}" title="${post.postTitle}">${post.postTitle}</a>
                                            <div class="post-time">
                                                <span class="date">${post.postDate?string("yyyy-MM-dd")}</span>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </ul>
                        </#list>
                    </@articleTag>
                </ul>
            </div>
            <!-- 归档列表 -->
            <#--<@articleTag method="archivesLess">-->
                <#--<#list archivesLess as archive>-->
                    <#--<div class="one-tag-list">-->
                        <#--<span class="fa fa-tag listing-seperator" &lt;#&ndash;id="${tag.tagUrl}"&ndash;&gt;>-->
                            <#--<span class="tag-text">${archive.year}</span>-->
                        <#--</span>-->
                        <#--&lt;#&ndash;<#list tag.posts as post>&ndash;&gt;-->
                        <#--<#list archive.posts?sort_by("postDate")?reverse as post>-->
                            <#--<div class="post-preview">-->
                                <#--<a href="/archives/${post.postUrl}" title="${post.postTitle}">-->
                                    <#--<h2 class="post-title">-->
                                        <#--<span>${post.postTitle}</span>-->
                                        <#--<span class="post-time">${post.postDate?string("yyyy-MM-dd")}</span>-->
                                    <#--</h2>-->
                                <#--</a>-->
                            <#--</div>-->
                            <#--<hr>-->
                        <#--</#list>-->
                    <#--</div>-->
                <#--</#list>-->
            <#--</@articleTag>-->
        </div>
    </div>
</div>
</@default>