<div class="sidebar animated fadeInDown">
    <div class="logo-title">
        <div class="title">
            <img src="${options.blog_logo?default("/anatole/source/images/logo-ruziji.png")}" style="width:127px;<#if options.anatole_style_avatar_circle?default('false')=='true'>border-radius:50%</#if>" />
            <h3 title="">
                <a href="/">${options.blog_title?default("ANATOLE")}</a>
            </h3>
            <div class="description">
                <#if options.anatole_style_hitokoto?default("false")=="true">
                    <p id="yiyan">获取中...</p>
                <#else >
                    <p>${user.userDesc?default("冷眼樵夫")}</p>
                </#if>
            </div>
        </div>
    </div>
    <#include "social-list.ftl">
    <div class="footer">
        <a target="_blank" href="#">
            <#-- 不允许修改该主题信息，也不能删除。 -->
            <span>Designed by </span>
            <a href="http://blog.ruzishici.top/">qiaofu</a>
            <#-- 虽然Lyblog使用了宽松的GPL协议，但开发不易，希望您可以保留一下版权声明。笔芯~ -->
            <div class="by_lyblog">
                <a href="https://github.com/yucang52555/lyblog.git" target="_blank">Proudly published with Lyblog&#65281;</a>
            </div>
            <div class="footer_text">
                <@footer_info></@footer_info>
            </div>
        </a>
    </div>
</div>
