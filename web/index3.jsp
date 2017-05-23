<%--
  Created by IntelliJ IDEA.
  User: ZhenZhen
  Date: 2017/5/21
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" import="java.util.*,java.text.*"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1" user-scalable="no">
    <meta name="description" content="ECharts, a powerful, interactive charting and visualization library for browser">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <script src="${pageContext.request.contextPath}/js/hm.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/pace.min.js"></script>
    <style>
        @font-face {font-family:"noto-thin";src:local("Microsoft Yahei");}@font-face {font-family:"noto-light";src:local("Microsoft Yahei");}</style>
    <script id="font-hack" type="text/javascript">
        if (/windows/i.test(navigator.userAgent)) {
            var el = document.createElement('style');
            el.innerHTML = ''
                + '@font-face {font-family:"noto-thin";src:local("Microsoft Yahei");}'
                + '@font-face {font-family:"noto-light";src:local("Microsoft Yahei");}';
            document.head.insertBefore(el, document.getElementById('font-hack'));
        }
    </script>
    <title>SVNCharts.</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/perfect-scrollbar.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>
<body class=" pace-done">
<div>
    <h1>来啊 快活啊 </h1>
</div>
<div class="pace  pace-inactive">
    <div class="pace-progress" data-progress-text="100%" data-progress="99" style="transform: translate3d(100%, 0px, 0px);">
        <div class="pace-progress-inner">
        </div>
    </div>
    <div class="pace-activity">
    </div>
</div>
<div id="lowie-main">
    <img src="${pageContext.request.contextPath}/images/forie.png" alt="ie tip">
</div>
<div id="main">
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false" class="navbar-toggle collapsed">
<span class="sr-only">
Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="http://echarts.baidu.com/index.html" class="navbar-brand">
                    <img src="${pageContext.request.contextPath}/images/logo.png" alt="echarts logo" class="navbar-logo">
                </a>
            </div>
            <div id="bs-example-navbar-collapse-1" class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li id="nav-index">
                        <a href="http://echarts.baidu.com/index.html">
                            首页</a>
                    </li>
                    <li id="nav-doc" class="dropdown">
                        <a href="${pageContext.request.contextPath}/index.jsp#" data-toggle="dropdown" class="dropdown-toggle">
                            文档<b class="caret">
                        </b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="http://echarts.baidu.com/tutorial.html">
                                    教程</a>
                            </li>
                            <li>
                                <a href="http://echarts.baidu.com/api.html">
                                    API</a>
                            </li>
                            <li>
                                <a href="http://echarts.baidu.com/option.html">
                                    配置项手册</a>
                            </li>
                            <li>
                                <a href="http://echarts.baidu.com/blog/index.html">
                                    博客</a>
                            </li>
                            <li>
                                <a href="http://echarts.baidu.com/changelog.html">
                                    CHANGELOG</a>
                            </li>
                        </ul>
                    </li>
                    <li id="nav-examples" class="active">
                        <a href="${pageContext.request.contextPath}/index.jsp#" data-toggle="dropdown" class="dropdown-toggle">
                            作品<b class="caret">
                        </b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="${pageContext.request.contextPath}/index.jsp">
                                    官方示例</a>
                            </li>
                            <li>
                                <a href="http://gallery.echartsjs.com/">
                                    GALLERY</a>
                            </li>
                        </ul>
                    </li>
                    <li id="nav-download" class="dropdown">
                        <a href="${pageContext.request.contextPath}/index.jsp#" data-toggle="dropdown" class="dropdown-toggle">
                            下载<b class="caret">
                        </b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="http://echarts.baidu.com/download.html">
                                    下载</a>
                            </li>
                            <li>
                                <a href="http://echarts.baidu.com/builder.html">
                                    在线构建</a>
                            </li>
                            <li>
                                <a href="http://echarts.baidu.com/download-map.html">
                                    地图下载</a>
                            </li>
                            <li>
                                <a href="http://ecomfe.github.io/echarts-map-tool/">
                                    地图构建</a>
                            </li>
                            <li>
                                <a href="http://echarts.baidu.com/download-theme.html">
                                    主题下载</a>
                            </li>
                            <li>
                                <a href="http://echarts.baidu.com/theme-builder/">
                                    主题构建</a>
                            </li>
                            <li>
                                <a href="http://echarts.baidu.com/spreadsheet.html">
                                    表格工具</a>
                            </li>
                        </ul>
                    </li>
                    <li id="nav-github">
                        <a href="https://github.com/ecomfe/echarts" target="_blank">
                            GitHub</a>
                    </li>
                    <li id="nav-2" class="0">
                        <a href="http://echarts.baidu.com/echarts2/">
                            2.0</a>
                    </li>
                    <li id="nav-homeen">
                        <a href="https://ecomfe.github.io/echarts-doc/public/en/index.html">
                            EN</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div id="nav-mask">
    </div>
    <div id="nav-layer" class="ps-container" data-ps-id="bc1a60bf-eedd-c896-b3f3-39e35d6ff896">
        <ul class="chart-list">
        </ul>
        <div class="ps-scrollbar-x-rail" style="left: 0px; bottom: 3px;">
            <div class="ps-scrollbar-x" tabindex="0" style="left: 0px; width: 0px;">
            </div>
        </div>
        <div class="ps-scrollbar-y-rail" style="top: 0px; right: 3px;">
            <div class="ps-scrollbar-y" tabindex="0" style="top: 0px; height: 0px;">
            </div>
        </div>
    </div>
    <div id="left-chart-nav" class="ps-container ps-active-y" data-ps-id="ee752e2a-5b81-4d17-a90e-f49b552c5b09">
        <ul>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-scatter" href="${pageContext.request.contextPath}/index.jsp#chart-type-scatter">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        散点图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-line" href="${pageContext.request.contextPath}/index.jsp#chart-type-line">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        折线图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-bar" href="${pageContext.request.contextPath}/index.jsp#chart-type-bar">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        柱状图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-map" href="${pageContext.request.contextPath}/index.jsp#chart-type-map">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        地图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-pie" href="${pageContext.request.contextPath}/index.jsp#chart-type-pie">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        饼图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-radar" href="${pageContext.request.contextPath}/index.jsp#chart-type-radar">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        雷达图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-candlestick" href="${pageContext.request.contextPath}/index.jsp#chart-type-candlestick">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        k线图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-boxplot" href="${pageContext.request.contextPath}/index.jsp#chart-type-boxplot">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        箱线图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-heatmap" href="${pageContext.request.contextPath}/index.jsp#chart-type-heatmap">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        热力图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-graph" href="${pageContext.request.contextPath}/index.jsp#chart-type-graph">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        关系图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-treemap" href="${pageContext.request.contextPath}/index.jsp#chart-type-treemap">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        矩形树图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-parallel" href="${pageContext.request.contextPath}/index.jsp#chart-type-parallel">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        平行坐标</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-sankey" href="${pageContext.request.contextPath}/index.jsp#chart-type-sankey">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        桑基图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-funnel" href="${pageContext.request.contextPath}/index.jsp#chart-type-funnel">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        漏斗图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-gauge" href="${pageContext.request.contextPath}/index.jsp#chart-type-gauge">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        仪表盘</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-pictorialBar" href="${pageContext.request.contextPath}/index.jsp#chart-type-pictorialBar">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        象形柱图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-themeRiver" href="${pageContext.request.contextPath}/index.jsp#chart-type-themeRiver">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        主题河流图</div>
                </a>
            </li>
            <li>
                <a class="left-chart-nav-link" id="left-chart-nav-calendar" href="${pageContext.request.contextPath}/index.jsp#chart-type-calendar">
                    <div class="chart-icon">
                    </div>
                    <div class="chart-name">
                        日历图</div>
                </a>
            </li>
        </ul>
        <div class="ps-scrollbar-x-rail" style="left: 0px; bottom: 3px;">
            <div class="ps-scrollbar-x" tabindex="0" style="left: 0px; width: 0px;">
            </div>
        </div>
        <div class="ps-scrollbar-y-rail" style="top: 0px; height: 894px; right: 3px;">
            <div class="ps-scrollbar-y" tabindex="0" style="top: 0px; height: 820px;">
            </div>
        </div>
    </div>
    <div id="explore-container">
        <div class="chart-list-panel">
            <h3 class="chart-type-head" id="chart-type-scatter">
                散点图</h3>
            <div class="row" id="chart-row-scatter">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#bubble-gradient">
                            <h4 class="chart-title">
                                方式的 气泡图</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/bubble-gradient.png" src="${pageContext.request.contextPath}/images/bubble-gradient.png" style="display: inline;">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-line">
                的是 折线图</h3>
            <div class="row" id="chart-row-line">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#line-polar">
                            <h4 class="chart-title">
                                极坐标双数值轴</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/line-polar.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-bar">
                柱状图</h3>
            <div class="row" id="chart-row-bar">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#multiple-y-axis">
                            <h4 class="chart-title">
                                多 Y 轴示例</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/multiple-y-axis.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-map">
                地图</h3>
            <div class="row" id="chart-row-map">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#map-HK">
                            <h4 class="chart-title">
                                香港18区人口密度 （2011）</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/map-HK.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-pie">
                饼图</h3>
            <div class="row" id="chart-row-pie">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#pie-roseType">
                            <h4 class="chart-title">
                                南丁格尔玫瑰图</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/pie-roseType.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-radar">
                雷达图</h3>
            <div class="row" id="chart-row-radar">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#radar2">
                            <h4 class="chart-title">
                                浏览器占比变化</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/radar2.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-candlestick">
                k线图</h3>
            <div class="row" id="chart-row-candlestick">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#candlestick-touch">
                            <h4 class="chart-title">
                                触屏上的坐标轴指示器</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/candlestick-touch.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-boxplot">
                箱线图</h3>
            <div class="row" id="chart-row-boxplot">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#boxplot-multi">
                            <h4 class="chart-title">
                                Multiple Categories</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/boxplot-multi.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-heatmap">
                热力图</h3>
            <div class="row" id="chart-row-heatmap">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#heatmap-map">
                            <h4 class="chart-title">
                                全国主要城市空气质量</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/heatmap-map.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-graph">
                关系图</h3>
            <div class="row" id="chart-row-graph">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#calendar-graph">
                            <h4 class="chart-title">
                                Calendar Graph</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/calendar-graph.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-treemap">
                矩形树图</h3>
            <div class="row" id="chart-row-treemap">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#treemap-visual">
                            <h4 class="chart-title">
                                Gradient Mapping</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/treemap-visual.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-parallel">
                平行坐标</h3>
            <div class="row" id="chart-row-parallel">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#scatter-matrix">
                            <h4 class="chart-title">
                                Scatter Matrix</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/scatter-matrix.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-sankey">
                桑基图</h3>
            <div class="row" id="chart-row-sankey">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#sankey-energy">
                            <h4 class="chart-title">
                                Sankey Diagram</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/sankey-energy.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-funnel">
                漏斗图</h3>
            <div class="row" id="chart-row-funnel">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#funnel-customize">
                            <h4 class="chart-title">
                                漏斗图</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/funnel-customize.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
            </div>
            <h3 class="chart-type-head" id="chart-type-gauge">
                仪表盘</h3>
            <div class="row" id="chart-row-gauge">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="chart">
                        <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#gauge-car-dark">
                            <h4 class="chart-title">
                                Gauge Car Dark</h4>
                            <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/gauge-car-dark.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                        </a>
                    </div>
                </div>
                <h3 class="chart-type-head" id="chart-type-pictorialBar">
                    象形柱图</h3>
                <div class="row" id="chart-row-pictorialBar">
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="chart">
                            <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#pictorialBar-hill">
                                <h4 class="chart-title">
                                    圣诞愿望清单和山峰高度</h4>
                                <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/pictorialBar-hill.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                            </a>
                        </div>
                    </div>
                    <h3 class="chart-type-head" id="chart-type-themeRiver">
                        主题河流图</h3>
                    <div class="row" id="chart-row-themeRiver">
                        <div class="col-lg-3 col-md-4 col-sm-6">
                            <div class="chart">
                                <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#themeRiver-lastfm">
                                    <h4 class="chart-title">
                                        ThemeRiver Lastfm</h4>
                                    <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/themeRiver-lastfm.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                                </a>
                            </div>
                        </div>
                    </div>
                    <h3 class="chart-type-head" id="chart-type-calendar">
                        日历图</h3>
                    <div class="row" id="chart-row-calendar">
                        <div class="col-lg-3 col-md-4 col-sm-6">
                            <div class="chart">
                                <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#calendar-lunar">
                                    <h4 class="chart-title">
                                        Calendar Lunar</h4>
                                    <img class="chart-area" data-original="http://echarts.baidu.com/gallery/data/thumb/calendar-lunar.png" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="display:none">
                点击查看详情</div>
        </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/validator.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/waypoint.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/perfect-scrollbar.min.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.lazyload.min.js">
        </script>
        <script type="text/javascript">
            var GALLERY_PATH = 'http://echarts.baidu.com/gallery/';
            console.log(GALLERY_PATH);
            var GALLERY_EDITOR_PATH = GALLERY_PATH + 'editor.html?c=';
            var GALLERY_VIEW_PATH = GALLERY_PATH + 'view.html?c=';
            var GALLERY_THUMB_PATH = GALLERY_PATH + 'data/thumb/';
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/config.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/chart-list.js">
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/examples-nav.js">
        </script>
        <script type="text/javascript">
            document.getElementById('nav-examples').className = 'active';</script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/hm(1).js">
        </script>
        <div style="position: static; width: 0px; height: 0px; border: none; padding: 0px; margin: 0px;">
            <div id="trans-tooltip">
                <div id="tip-left-top" style="background: url(&quot;chrome-extension://ikkepelhgbcgmhhmcmpfkjmchccjblkd/imgs/map/tip-left-top.png&quot;);">
                </div>
                <div id="tip-top" style="background: url(&quot;chrome-extension://ikkepelhgbcgmhhmcmpfkjmchccjblkd/imgs/map/tip-top.png&quot;) repeat-x;">
                </div>
                <div id="tip-right-top" style="background: url(&quot;chrome-extension://ikkepelhgbcgmhhmcmpfkjmchccjblkd/imgs/map/tip-right-top.png&quot;);">
                </div>
                <div id="tip-right" style="background: url(&quot;chrome-extension://ikkepelhgbcgmhhmcmpfkjmchccjblkd/imgs/map/tip-right.png&quot;) repeat-y;">
                </div>
                <div id="tip-right-bottom" style="background: url(&quot;chrome-extension://ikkepelhgbcgmhhmcmpfkjmchccjblkd/imgs/map/tip-right-bottom.png&quot;);">
                </div>
                <div id="tip-bottom" style="background: url(&quot;chrome-extension://ikkepelhgbcgmhhmcmpfkjmchccjblkd/imgs/map/tip-bottom.png&quot;) repeat-x;">
                </div>
                <div id="tip-left-bottom" style="background: url(&quot;chrome-extension://ikkepelhgbcgmhhmcmpfkjmchccjblkd/imgs/map/tip-left-bottom.png&quot;);">
                </div>
                <div id="tip-left" style="background: url(&quot;chrome-extension://ikkepelhgbcgmhhmcmpfkjmchccjblkd/imgs/map/tip-left.png&quot;);">
                </div>
                <div id="trans-content">
                </div>
            </div>
            <div id="tip-arrow-bottom" style="background: url(&quot;chrome-extension://ikkepelhgbcgmhhmcmpfkjmchccjblkd/imgs/map/tip-arrow-bottom.png&quot;);">
            </div>
            <div id="tip-arrow-top" style="background: url(&quot;chrome-extension://ikkepelhgbcgmhhmcmpfkjmchccjblkd/imgs/map/tip-arrow-top.png&quot;);">
            </div>
        </div>
        <div>
</body>
</html>
</html>

