<#include "common_macro.ftl">
<#macro default title="" keywords="" description="">
<!DOCTYPE html>
<html lang="zh-CN">

    <#include "head.ftl">
    <@head title="${title}" keywords="${keywords}" description="${description}"></@head>

<!-- hack iOS CSS :active style -->
<body ontouchstart="">

    <#include "nav.ftl">

    <#nested>

    <#include "footer.ftl">

<!-- Image to hack wechat -->
<img src="/${themeName}/source/img/icon_wechat.png" width="0" height="0"/>
<!-- Migrate from head to bottom, no longer block render and still work -->

</body>

</html>
</#macro>
