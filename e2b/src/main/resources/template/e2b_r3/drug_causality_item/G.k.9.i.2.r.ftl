<#assign valueMap=causalityItem.valueMap>
<#if valueMap['G.k.9.i.2.r.1'] == '公司' || valueMap['G.k.9.i.2.r.1'] == '报告者' || valueMap['G.k.9.i.2.r.1'] == 'company' || valueMap['G.k.9.i.2.r.1'] == 'reporter'>

<component typeCode="COMP">
    <causalityAssessment classCode="OBS" moodCode="EVN">
        <code code="39" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1" displayName="causality"/>
        <value xsi:type="ST">${valueMap['G.k.9.i.2.r.3']?if_exists?xml}</value>
		<#if isDebug == 1><!-- G.k.9.i.2.r.3 --></#if>
        <methodCode>
            <originalText>${valueMap['G.k.9.i.2.r.2']?if_exists?xml}</originalText>
			<#if isDebug == 1><!-- G.k.9.i.2.r.2 --></#if>
        </methodCode>
        <author typeCode="AUT">
            <assignedEntity classCode="ASSIGNED">
                <code>
                    <originalText>${valueMap['G.k.9.i.2.r.1']?if_exists?xml}</originalText>
					<#if isDebug == 1><!-- G.k.9.i.2.r.1 --></#if>
                </code>
            </assignedEntity>
        </author>
        <subject1 typeCode="SUBJ">
            <adverseEffectReference classCode="OBS" moodCode="EVN">
                <id root="${valueMap['reference.fileEventId']?if_exists?xml}"/>
            </adverseEffectReference>
        </subject1>
        <#if isDebug == 1><!-- fileEventId --></#if>
        <subject2 typeCode="SUBJ">
            <productUseReference classCode="SBADM" moodCode="EVN">
                <id root="${valueMap['reference.fileDrugId']?if_exists?xml}"/>
            </productUseReference>
        </subject2>
        <#if isDebug == 1><!-- fileDrugId --></#if>
    </causalityAssessment>
</component>


<#else>
               <component typeCode="COMP">
                   <causalityAssessment classCode="OBS" moodCode="EVN">
                       <code code="39" codeSystem="2.16.840.1.113883.3.989.2.1.1.19"
                             codeSystemVersion="2.0" displayName="causality"/>
                       <value code="${valueMap['G.k.9.i.2.r.3']?if_exists?xml}" codeSystem="2.16.840.1.113883.3.989.5.1.1.5.3"
                              codeSystemVersion="1.0" displayName="Reasonable possibility"
                              xsi:type="CE"/>
                       <#if isDebug == 1><!-- G.k.9.i.2.r.3 --></#if>
                       <methodCode code="1"
                                   codeSystem="2.16.840.1.113883.3.989.5.1.1.5.2"
                                   codeSystemVersion="1.0"
                                   displayName="EU Method of Assessment"/>
                       <#if isDebug == 1><!-- G.k.9.i.2.r.2 --></#if>
                       <author typeCode="AUT">
                           <assignedEntity classCode="ASSIGNED">
                               <code code="${valueMap['G.k.9.i.2.r.1']?if_exists?xml}"
                                     codeSystem="2.16.840.1.113883.3.989.5.1.1.5.4"
                                     codeSystemVersion="1.0" displayName="Sponsor"/>
                               <#if isDebug == 1><!-- G.k.9.i.2.r.1 --></#if>
                           </assignedEntity>
                       </author>
                       <subject1 typeCode="SUBJ">
                           <adverseEffectReference classCode="OBS" moodCode="EVN">
                               <id root="${valueMap['reference.fileEventId']?if_exists?xml}"/>
                                    <#if isDebug == 1><!-- fileEventId --></#if>
                           </adverseEffectReference>
                       </subject1>
                       <subject2 typeCode="SUBJ">
                           <productUseReference classCode="SBADM" moodCode="EVN">
                               <id root="${valueMap['reference.fileDrugId']?if_exists?xml}"/>
                                       <#if isDebug == 1><!-- fileDrugId --></#if>
                           </productUseReference>
                       </subject2>
                   </causalityAssessment>
               </component>
</#if>
