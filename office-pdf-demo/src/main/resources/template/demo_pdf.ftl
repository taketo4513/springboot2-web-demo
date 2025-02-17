<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <style type="text/css">
        body {
            font-family: SimSun;
        }

        .mybody h1 {
            font-size: 24px;
            text-align: center;
            line-height: 30px;
            padding: 20px 0;
            padding-top: 30px;
        }

        .content_class p {
            display: block;
            margin-block-start: 1em;
            margin-block-end: 1em;
            margin-inline-start: 0px;
            margin-inline-end: 0px;
        }

        .content_class {
            box-shadow: none;
            line-height: 30px;
            margin: 15px auto;
            height: auto !important;
            padding: 30px 75px 100px 75px !important;
        }

        .t_tail {
            position: relative;
            text-align: right;
        }

        .Gzimg {
            position: absolute;
            left: 400px;
            top: -35px;
            width: 140px;
            height: 140px;
        }
    </style>
</head>
<body>
<div class="mybody">
    <div class="header"></div>
    <h1>PDF导出模板<br/>demo</h1>
    <div class="content_class">
        <p>配置参数<u>${data}</u></p>
        <p class="t_tail">单位名称<br/>
            <img src="${image}" class="Gzimg"/>
        </p>
        <p class="t_tail">${date}</p>
    </div>
    <div class="footer"></div>
</div>
</body>
</html>
