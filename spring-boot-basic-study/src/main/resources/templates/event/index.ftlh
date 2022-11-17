<#--<!--FreeMarker 적용&ndash;&gt;-->
<#--<!DOCTYPE html>-->
<#--<html lang="ko">-->
<#--<head>-->
<#--    <meta charset="UTF-8">-->
<#--    <title>Title</title>-->
<#--</head>-->
<#--<body>-->
<#--    <p>-->
<#--        <span id="eventTitle">This is events page.</span>-->
<#--    </p>-->
<#--    <table>-->
<#--            <thread>-->
<#--                <tr>-->
<#--                    <th>장소 ID</th>-->
<#--                    <th>이벤트명</th>-->
<#--                    <th>이벤트 상태</th>-->
<#--                    <th>시작 시간</th>-->
<#--                    <th>종료 시간</th>-->
<#--                    <th>전체 인원</th>-->
<#--                    <th>최대 수용 인원</th>-->
<#--                    <th>상세</th>-->
<#--                </tr>-->
<#--            </thread>-->
<#--            <tbody>-->
<#--            <#list events as event>-->
<#--                <tr>-->
<#--                    <td>${event.palceId()!"-"}</td>-->
<#--                    <td>${event.eventName()!"-"}</td>-->
<#--                    <td>${event.eventStatus()!"-"}</td>-->
<#--                    <td>${event.eventStartDatetime()!"-"}</td>-->
<#--                    <td>${event.eventEndDatetime()!"-"}</td>-->
<#--                    <td>${event.currentNumberOfPeople()!"-"}</td>-->
<#--                    <td>${event.capacity()!"-"}</td>-->
<#--&lt;#&ndash;                    <td><a href="">상세</a></td>&ndash;&gt;-->
<#--                </tr>-->
<#--            </#list>-->
<#--            </tbody>-->
<#--    </table>-->
<#--</body>-->
<#--</html>-->