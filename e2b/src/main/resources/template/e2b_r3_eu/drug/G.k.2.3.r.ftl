<#assign valueMap2=psur.valueMap>
<ingredient classCode="ACTI">
    <#if valueMap2['G.k.2.3.r.3a']?exists>
    <quantity>
        <numerator value="${valueMap2['G.k.2.3.r.3a']?if_exists?xml}" unit="${valueMap2['G.k.2.3.r.3b']?if_exists?xml}"/>
		<#if isDebug == 1><!-- G.k.2.3.r.3a --></#if> <#if isDebug == 1><!-- G.k.2.3.r.3b --></#if>
        <denominator value="1"/>
    </quantity>
    </#if>
    <ingredientSubstance classCode="MMAT" determinerCode="KIND">
        <#if valueMap2['G.k.2.3.r.2b']?exists>
        <code code="${valueMap2['G.k.2.3.r.2b']?if_exists?xml}" codeSystem="TBD-Substance" codeSystemVersion="${valueMap2['G.k.2.3.r.2a']?if_exists?xml}"/>
		<#if isDebug == 1><!-- G.k.2.3.r.2b --></#if> <#if isDebug == 1><!-- G.k.2.3.r.2a --></#if>
        </#if>
        <name>${valueMap2['G.k.2.3.r.1']?if_exists?xml}</name>
		<#if isDebug == 1><!-- G.k.2.3.r.1 --></#if>
    </ingredientSubstance>
</ingredient>