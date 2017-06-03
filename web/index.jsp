<%@ taglib prefix="vertical-align" uri="/struts-tags" %>
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
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-ui.min.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/perfect-scrollbar.min.css">
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.json-2.4.js"></script>
  <script src="${pageContext.request.contextPath}/js/hm.js"></script>
  <script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
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

  <script>
      $(function() {
          $( "#date" ).datepicker({
              showButtonPanel: true,
              changeMonth: true,
              changeYear: true,
              dateFormat: 'yy-m-d'
          });
      });

  </script>
</head>
<body class=" pace-done">
<div></div>
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

          <li id="nav-doc" class="dropdown">
            <a href="${pageContext.request.contextPath}/index.jsp#" data-toggle="dropdown" class="dropdown-toggle">
              文档<b class="caret">
            </b></a></li>
          <li id="nav-download" class="dropdown">
            <a href="${pageContext.request.contextPath}/index.jsp#" data-toggle="dropdown" class="dropdown-toggle">
              下载<b class="caret">
            </b>
            </a>
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
            首页</div>
        </a>
      </li>
      <li>
        <a class="left-chart-nav-link" id="left-chart-nav-line" href="${pageContext.request.contextPath}/index.jsp#chart-type-line">
          <div class="chart-icon">
          </div>
          <div class="chart-name">
            按时间查看</div>
        </a>
      </li>
      <li>
        <a class="left-chart-nav-link" id="left-chart-nav-bar" href="${pageContext.request.contextPath}/index.jsp#chart-type-bar">
          <div class="chart-icon">
          </div>
          <div class="chart-name">
            按人员查看</div>
        </a>
      </li>
      <li>
        <a class="left-chart-nav-link" id="left-chart-nav-map" href="${pageContext.request.contextPath}/index.jsp#chart-type-map">
          <div class="chart-icon">
          </div>
          <div class="chart-name">
            按文件查看</div>
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
        <br/><br/>首页</h3>
      <div class="row" id="chart-row-scatter">
        <div class="col-lg-3 col-md-4 col-sm-6" style="width:800px;">
          <form id="updateData">
            <tr>
              <th>SVN Repository: <input type="text" id="SVNRepository" size="50" value="http://svn.apache.org/repos/asf/tomcat/trunk/"></th>
              <th><input type="button" id="updateResp" value="更新数据" ></th>
            </tr>
          </form>
        </div>
        <div class="col-lg-3 col-md-4 col-sm-6" style="width:1000px;">
          Status: </br>
          <textarea name="" id="statusText" cols="70" rows="8" style="width:552px"></textarea>
        </div>
      </div>


      <h3 class="chart-type-head" id="chart-type-line">
        <br/><br/>代码统计</h3>

      <div  style="margin-bottom: 10px;width: 460px">

          <table>
            <tr>
              <th>Developer：<select id="nameSelect"><option value="All">All</option></select></th>
              <th>Date : <input type="text" id="date" ></th>
              <th><input type="button" id="ok" value="   ok   " ></th>
            </tr>
          </table>
          <script type="text/javascript">document.getElementById('date').value=new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'</script>

      </div>

      <div class="row" id="chart-row-line">
        <div class="col-lg-3 col-md-4 col-sm-6" style="width:800px;height: 500px;" vertical-align:vertical-align: middle;>
          <center><h4 class="chart-title" >修改文件个数</h4></center>
          <center>
            <div  id="AMDcounts" style="width:700px; height:500px;"> </div>
          </center>
          <center>
          </center>
        </div>
        <div class="col-lg-3 col-md-4 col-sm-6" style="width:800px;height: 500px;" vertical-align:vertical-align: middle;>
        <center><h4 class="chart-title" >影响文件类型</h4></center>
        <center>
          <div  id="fileType" style="width:700px; height:500px;"> </div>
        </center>
        <center>
        </center>
      </div>
        <div class="col-lg-3 col-md-4 col-sm-6" style="width:800px;height: 500px;" vertical-align:vertical-align: middle;>
          <center><h4 class="chart-title" ><br/><br/>代码增删行数</h4></center>
          <center>
            <div  id="code" style="width:700px; height:500px;"> </div>
          </center>
          <center>
          </center>
        </div>
        <div class="col-lg-3 col-md-4 col-sm-6" style="width:800px;height: 500px;" vertical-align:vertical-align: middle;>
          <center><h4 class="chart-title" ><br/><br/>修改类型（bug或其他）</h4></center>
          <center>
            <div  id="fixType" style="width:700px; height:500px;"> </div>
          </center>
          <center>
          </center>
        </div>
      </div>
      <h3 class="chart-type-head" id="chart-type-bar">
        <br/><br/>按人员查看</h3>
      <div class="row" id="chart-row-bar">
        <div style="margin-left: 15px;margin-bottom: 10px">
          <form action="" method="post">
            <table>
              <tr>
                <th>Developer：<select ></select></th>
                <th>&nbsp;Date：<input type="text"  ></th>
                <th><input type="button"  value="  ok  " id="nameOk"></th>
              </tr>
            </table>
          </form>

        </div>
        <div class="col-lg-3 col-md-4 col-sm-6" style="width:800px;height: 500px;" vertical-align:vertical-align: middle;>
          <center><h4 class="chart-title" >修改情况柱状图</h4></center>
          <center>
            <div   style="width:700px; height:450px;"> </div>
          </center>
          <center>
            <div style="width:400px;"  >
              <form action="" method="post">
                <table>
                  <tr>
                    <th>Date : <input type="text"  ></th>
                    <th><input type="button"  value="ok" ></th>
                  </tr>
                </table>
              </form>
            </div>
          </center>
        </div>
        <div class="col-lg-3 col-md-4 col-sm-6">
          <div class="chart">
            <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#line-polar">
              <h4 class="chart-title">
                照骗</h4>
              <img class="chart-area"  src="${pageContext.request.contextPath}/images/president.jpg">
            </a>
          </div>
        </div>
      </div>
      <h3 class="chart-type-head" id="chart-type-map">
        <br/><br/>按文件查看</h3>
      <div class="row" id="chart-row-map">
        <div class="col-lg-3 col-md-4 col-sm-6" style="width:800px;height: 500px;" vertical-align:vertical-align: middle;>
          <center><h4 class="chart-title" >修改情况柱状图</h4></center>
          <center>
            <div   style="width:700px; height:450px;"> </div>
          </center>
          <center>
            <div style="width:400px;"  >
              <form action="" method="post">
                <table>
                  <tr>
                    <th>Date : <input type="text"  ></th>
                    <th><input type="button"  value="ok" ></th>
                  </tr>
                </table>
              </form>
            </div>
          </center>
        </div>
        <div class="col-lg-3 col-md-4 col-sm-6">
          <div class="chart">
            <a class="chart-link" href="${pageContext.request.contextPath}/index.jsp#line-polar">
              <h4 class="chart-title">
                照骗</h4>
              <img class="chart-area"  src="${pageContext.request.contextPath}/images/president.jpg">
            </a>
          </div>
        </div>
      </div>
      </div>
        </div>
      </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/AMDjs.js"></script><!--AMD查询的js-->
<script src="${pageContext.request.contextPath}/js/shouye.js"></script><!--首页模块的js-->


 </body>
</html>

