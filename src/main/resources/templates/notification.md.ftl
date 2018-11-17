Execution of job [<#t>
<#if execution.job.group?has_content>${execution.job.group}/</#if>${execution.job.name} <#t>
#${execution.context.job.execid}<#t>
](${execution.job.href}) <#t>
by ${execution.context.job.username} <#t>
<#if trigger == "start">
 **started**
<#elseif trigger == "failure">
 **failed**
<#elseif trigger == "success">
 **succeeded**
</#if>
