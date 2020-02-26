<#assign valueMap2=dose.valueMap>
<#assign nullflavorValueMap2=dose.nullflavorValueMap>
<outboundRelationship2 typeCode="COMP">
    <substanceAdministration classCode="SBADM" moodCode="EVN">
        <#if valueMap2['G.k.4.r.8']?exists>
        <text>${valueMap2['G.k.4.r.8']?if_exists?xml}</text>
                <#if isDebug == 1><!-- G.k.4.r.8 --></#if>
        </#if>

    <#-- G.k.4.r complex logic start -->
        <#if valueMap2["G.k.4.r.format"] == "3COMP">
            <effectiveTime xsi:type="SXPR_TS">
                <comp xsi:type="PIVL_TS">
                    <period value="${valueMap2['G.k.4.r.2']?if_exists?xml}" unit="${valueMap2['G.k.4.r.3']?if_exists?xml}"/>
				    <#if isDebug == 1><!-- G.k.4.r.2 --></#if> <#if isDebug == 1><!-- G.k.4.r.3 --></#if>
                </comp>
                <comp xsi:type="IVL_TS" operator="A">
                    <#if valueMap2['G.k.4.r.4']?exists && valueMap2['G.k.4.r.4'] !="">
                        <low value="${valueMap2['G.k.4.r.4']?if_exists?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.4 --></#if>
                    <#elseif nullflavorValueMap2['G.k.4.r.4']?has_content>
                        <low nullFlavor="${nullflavorValueMap2['G.k.4.r.4']?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.4 nullflavor --></#if>
                    </#if>


                    <#if valueMap2['G.k.4.r.5']?exists && valueMap2['G.k.4.r.5'] !="">
                        <high value="${valueMap2['G.k.4.r.5']?if_exists?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.5 --></#if>
                    <#elseif nullflavorValueMap2['G.k.4.r.5']?has_content>
                        <high nullFlavor="${nullflavorValueMap2['G.k.4.r.5']?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.5 nullflavor--></#if>
                    </#if>

                </comp>
                <comp xsi:type="IVL_TS" operator="A">
                    <width value="${valueMap2['G.k.4.r.6a']?if_exists?xml}" unit="${valueMap2['G.k.4.r.6b']?if_exists?xml}"/>
			        <#if isDebug == 1><!-- G.k.4.r.6a --></#if> <#if isDebug == 1><!-- G.k.4.r.6b --></#if>
                </comp>
            </effectiveTime>
        <#elseif valueMap2["G.k.4.r.format"] == "2COMP">
            <effectiveTime xsi:type="SXPR_TS">
                <comp xsi:type="PIVL_TS">
                    <period value="${valueMap2['G.k.4.r.2']?if_exists?xml}" unit="${valueMap2['G.k.4.r.3']?if_exists?xml}"/>
				    <#if isDebug == 1><!-- G.k.4.r.2 --></#if> <#if isDebug == 1><!-- G.k.4.r.3 --></#if>
                </comp>
                <comp xsi:type="IVL_TS" operator="A">
                    <#if valueMap2['G.k.4.r.4']?exists && valueMap2['G.k.4.r.4'] !="">
                        <low value="${valueMap2['G.k.4.r.4']?if_exists?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.4 --></#if>
                    <#elseif nullflavorValueMap2['G.k.4.r.4']?has_content>
                        <low nullFlavor="${nullflavorValueMap2['G.k.4.r.4']?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.4 nullflavor--></#if>
                    </#if>


                    <#if valueMap2['G.k.4.r.5']?exists && valueMap2['G.k.4.r.5'] !="">
                        <high value="${valueMap2['G.k.4.r.5']?if_exists?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.5 --></#if>
                    <#elseif nullflavorValueMap2['G.k.4.r.5']?has_content>
                        <high nullFlavor="${nullflavorValueMap2['G.k.4.r.5']?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.5 nullflavor --></#if>
                    </#if>


                    <#if valueMap2['G.k.4.r.6a']?exists && valueMap2['G.k.4.r.6a'] !="">
                        <width value="${valueMap2['G.k.4.r.6a']?if_exists?xml}"
                               unit="${valueMap2['G.k.4.r.6b']?if_exists?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.6a --></#if> <#if isDebug == 1><!-- G.k.4.r.6b --></#if>

                    </#if>
                </comp>
            </effectiveTime>
        <#elseif valueMap2["G.k.4.r.format"] == "PIVL">
            <effectiveTime xsi:type="PIVL_TS">
                <period value="${valueMap2['G.k.4.r.2']?if_exists?xml}" unit="${valueMap2['G.k.4.r.3']?if_exists?xml}"/>
                <#if isDebug == 1><!-- G.k.4.r.2 --></#if> <#if isDebug == 1><!-- G.k.4.r.3 --></#if>
            </effectiveTime>
        <#elseif valueMap2["G.k.4.r.format"] == "IVL">
            <effectiveTime xsi:type="IVL_TS">
                    <#if valueMap2['G.k.4.r.4']?exists && valueMap2['G.k.4.r.4'] !="">
                        <low value="${valueMap2['G.k.4.r.4']?if_exists?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.4 --></#if>
                    <#elseif nullflavorValueMap2['G.k.4.r.4']?has_content>
                        <low nullFlavor="${nullflavorValueMap2['G.k.4.r.4']?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.4 nullflavor--></#if>
                    </#if>


                    <#if valueMap2['G.k.4.r.5']?exists && valueMap2['G.k.4.r.5'] !="">
                        <high value="${valueMap2['G.k.4.r.5']?if_exists?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.5 --></#if>
                    <#elseif nullflavorValueMap2['G.k.4.r.5']?has_content>
                        <high nullFlavor="${nullflavorValueMap2['G.k.4.r.5']?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.5 nullflavor--></#if>
                    </#if>


                    <#if valueMap2['G.k.4.r.6a']?exists && valueMap2['G.k.4.r.6a'] !="">
                        <width value="${valueMap2['G.k.4.r.6a']?if_exists?xml}"
                               unit="${valueMap2['G.k.4.r.6b']?if_exists?xml}"/>
                            <#if isDebug == 1><!-- G.k.4.r.6a --></#if> <#if isDebug == 1><!-- G.k.4.r.6b --></#if>

                    </#if>
            </effectiveTime>
        <#elseif valueMap2["G.k.4.r.format"] == "2COMP_IVL">
                    <effectiveTime xsi:type="SXPR_TS">
                        <comp xsi:type="IVL_TS">
                        <#if valueMap2['G.k.4.r.4']?exists && valueMap2['G.k.4.r.4'] !="">
                            <low value="${valueMap2['G.k.4.r.4']?if_exists?xml}"/>
                                <#if isDebug == 1><!-- G.k.4.r.4 --></#if>
                        <#elseif nullflavorValueMap2['G.k.4.r.4']?has_content>
                            <low nullFlavor="${nullflavorValueMap2['G.k.4.r.4']?xml}"/>
                                <#if isDebug == 1><!-- G.k.4.r.4 nullflavor--></#if>
                        </#if>


                        <#if valueMap2['G.k.4.r.5']?exists && valueMap2['G.k.4.r.5'] !="">
                            <high value="${valueMap2['G.k.4.r.5']?if_exists?xml}"/>
                                <#if isDebug == 1><!-- G.k.4.r.5 --></#if>
                        <#elseif nullflavorValueMap2['G.k.4.r.5']?has_content>
                            <high nullFlavor="${nullflavorValueMap2['G.k.4.r.5']?xml}"/>
                                <#if isDebug == 1><!-- G.k.4.r.5 nullflavor --></#if>
                        </#if>

                        </comp>
                        <comp xsi:type="IVL_TS" operator="A">
                            <width value="${valueMap2['G.k.4.r.6a']?if_exists?xml}"
                                   unit="${valueMap2['G.k.4.r.6b']?if_exists?xml}"/>
                                    <#if isDebug == 1><!-- G.k.4.r.6a --></#if>
                                <#if isDebug == 1><!-- G.k.4.r.6b --></#if>
                        </comp>
                    </effectiveTime>
        </#if>
    <#-- G.k.4.r complex logic end -->


        <#if valueMap2['G.k.4.r.10.1']?exists>
        <routeCode codeSystem="2.16.840.1.113883.3.989.2.1.1.14">
		    <#if isDebug == 1><!-- G.k.4.r.10.2b --></#if> <#if isDebug == 1><!-- G.k.4.r.10.2a --></#if>
            <originalText>${valueMap2['G.k.4.r.10.1']?if_exists?xml}</originalText>
		    <#if isDebug == 1><!-- G.k.4.r.10.1 --></#if>
        </routeCode>
        <#elseif nullflavorValueMap2['G.k.4.r.10.1']?has_content>
         <routeCode codeSystem="2.16.840.1.113883.3.989.2.1.1.14">
		    <#if isDebug == 1><!-- G.k.4.r.10.2b --></#if> <#if isDebug == 1><!-- G.k.4.r.10.2a --></#if>
             <originalText nullFlavor="${nullflavorValueMap2['G.k.4.r.10.1']?if_exists?xml}"/>
		    <#if isDebug == 1><!-- G.k.4.r.10.1 nullflavor--></#if>
         </routeCode>
        </#if>
        <#if valueMap2['G.k.4.r.1a']?has_content && valueMap2['G.k.4.r.1b']?has_content>
        <doseQuantity value="${valueMap2['G.k.4.r.1a']?if_exists?xml}" unit="${valueMap2['G.k.4.r.1b']?if_exists?xml}"/>
                <#if isDebug == 1><!-- G.k.4.r.1a --></#if> <#if isDebug == 1><!-- G.k.4.r.1b --></#if>
        </#if>
        <#if valueMap2['G.k.4.r.7']?exists||valueMap2['G.k.4.r.9.1']?exists>
        <consumable typeCode="CSM">
            <instanceOfKind classCode="INST">
                <productInstanceInstance classCode="MMAT" determinerCode="INSTANCE">
                <#if valueMap2['G.k.4.r.7']?exists>
                    <lotNumberText>${valueMap2['G.k.4.r.7']?if_exists?xml}</lotNumberText>
					<#if isDebug == 1><!-- G.k.4.r.7 --></#if>
                </#if>
                </productInstanceInstance>
                <#if valueMap2['G.k.4.r.9.1']?exists>
                <kindOfProduct classCode="MMAT" determinerCode="KIND">
                    <formCode codeSystem="TBD-DoseForm">
					<#if isDebug == 1><!-- G.k.4.r.9.2b --></#if>
                        <originalText>${valueMap2['G.k.4.r.9.1']?if_exists?xml}</originalText>
					    <#if isDebug == 1><!-- G.k.4.r.9.1 --></#if>
                    </formCode>
                </kindOfProduct>
                <#elseif nullflavorValueMap2['G.k.4.r.9.1']?has_content>
                <kindOfProduct classCode="MMAT" determinerCode="KIND">
                    <formCode codeSystem="TBD-DoseForm">
					<#if isDebug == 1><!-- G.k.4.r.9.2b --></#if>
                        <originalText nullFlavor="${nullflavorValueMap2['G.k.4.r.9.1']?if_exists?xml}"/>
					    <#if isDebug == 1><!-- G.k.4.r.9.1 nullflavor--></#if>
                    </formCode>
                </kindOfProduct>
                </#if>
            </instanceOfKind>
        </consumable>
        </#if>
<#if valueMap2['G.k.4.r.11.1']?exists>
        <inboundRelationship typeCode="REFR">
            <observation classCode="OBS" moodCode="EVN">
                <code code="28" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                      displayName="parentRouteOfAdministration"/>
                <value xsi:type="CE" codeSystem="2.16.840.1.113883.3.989.2.1.1.14">
                    <originalText>${valueMap2['G.k.4.r.11.1']?if_exists?xml}</originalText>
                    <#if isDebug == 1><!-- G.k.4.r.11.1 --></#if>
                </value>
            </observation>
        </inboundRelationship>
<#elseif nullflavorValueMap2['G.k.4.r.11.1']?has_content>
        <inboundRelationship typeCode="REFR">
            <observation classCode="OBS" moodCode="EVN">
                <code code="28" codeSystem="2.16.840.1.113883.3.989.2.1.1.19" codeSystemVersion="1.1"
                      displayName="parentRouteOfAdministration"/>
                <value xsi:type="CE" codeSystem="2.16.840.1.113883.3.989.2.1.1.14">
                    <originalText nullFlavor="${nullflavorValueMap2['G.k.4.r.11.1']?xml}" />
                    <#if isDebug == 1><!-- G.k.4.r.11.1 nullflavor--></#if>
                </value>
            </observation>
        </inboundRelationship>
</#if>

    </substanceAdministration>
</outboundRelationship2>
