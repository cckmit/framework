<#assign valueMap2=moreInfo.valueMap>
<outboundRelationship2 typeCode="REFR">
    <observation classCode="OBS" moodCode="EVN">
        <code code="9"
              codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
              codeSystemVersion="1.1"
              displayName="codedDrugInformation"/>
        <#if isDebug == 1><!-- G.k.10.r --></#if>
        <value xsi:type="CE" code="${valueMap2['G.k.10.r']?if_exists?xml}"
               codeSystem="2.16.840.1.113883.3.989.2.1.1.17"
               codeSystemVersion="1.0"/>
    </observation>
</outboundRelationship2>
