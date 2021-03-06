<#macro head title="" keywords="" description="个人博客,记录一下个人的工作经验、心路感悟">
<head>
    <!-- google站点优化 -->
    <meta name="google-site-verification" content="UEFgToH89wM9G2Z9hYypotBGJtFMMgYo85N6KmI60cc" />
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="google-site-verification" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="冷眼樵夫，樵夫博客,个人博客,记录一下个人的工作经验、心路感悟！ ${description}">
    <meta name="keywords" content="冷眼樵夫,樵夫博客,个人博客 ${keywords}">
    <meta name="baidu-site-verification" content="GOpy0NK9kt" />
    <@verification></@verification>
    <@favicon></@favicon>

    <title>${title}</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="/${themeName}/source/css/bootstrap.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="/${themeName}/source/css/hux-blog.min.css">

    <!-- Pygments Github CSS -->
    <link rel="stylesheet" href="/${themeName}/source/css/syntax.css">

    <!-- Custom Fonts -->
    <!-- <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css"> -->
    <!-- Hux change font-awesome CDN to qiniu -->
    <link href="//cdnjs.loli.net/ajax/libs/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">


    <link href='//fonts.loli.net/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='//fonts.loli.net/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="//oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- ga & ba script hoook -->
    <script></script>
    <!-- 百度统计 -->
    <script>
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "https://hm.baidu.com/hm.js?fcaded1f4d1c0294b4a9ef12843c9f37";
      var s = document.getElementsByTagName("script")[0];
      s.parentNode.insertBefore(hm, s);
    })();
    </script>

    <!-- google 广告-->
    <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
    <script>
        (adsbygoogle = window.adsbygoogle || []).push({
            google_ad_client: "ca-pub-6488119633221249",
            enable_page_level_ads: true
        });
    </script>
</head>
</#macro>
