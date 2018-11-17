Execution of job <#t>
<#if execution.job.group?has_content>${execution.job.group}/</#if>${execution.job.name} <#t>
#${execution.context.job.execid} <#t>
by ${execution.context.job.username} <#t>
<#if trigger == "start">
 started: <#t>
<#elseif trigger == "failure">
 failed: <#t>
<#elseif trigger == "success">
 succeeded: <#t>
</#if>
${execution.job.href}
