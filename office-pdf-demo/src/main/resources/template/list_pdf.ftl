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

        .mybody h3 {
            font-size: 24px;
            text-align: center;
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

        .Gzimg {
            position: absolute;
            left: 400px;
            top: -35px;
            width: 140px;
            height: 140px;
        }

        .table tb1 {
            border-collapse: collapse;
            margin: 0px auto;
        }

        .table tr {
            width: 700px;
            text-align: center;
            border: 1px solid #000000;
        }

        .table td {
            border: 1px solid;
            text-align: center;
            vertical-align: middle;
        }

        .table th {
            margin: 0 auto;
            text-align: center;
            border: 1px solid;
        }
    </style>
</head>
<body>
<div class="mybody">
    <div class="header"></div>
    <h1>PDF导出列表</h1>
    <h3>demo</h3>
    <div class="content_class">
        <table border="1" width="500" cellspacing="0" cellpadding="0" style="table-layout:fixed; word-wrap:break-word; word-break:break-all">
            <thead>
            <tr>
                <th style="text-align: center">序号</th>
                <th style="text-align: center">demo</th>
            </tr>
            </thead>
            <tbody>
            <#assign oneNum=1/>
            <#list queryList as list>
                <tr>
                    <td style="text-align: center">${oneNum}</td>
                    <td style="text-align: center">${list.demo}</td>
                </tr>
                <#assign oneNum++/>
            </#list>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
