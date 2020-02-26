<#assign valueMap=reporter.valueMap>
<#assign nullflavorValueMap=reporter.nullflavorValueMap>
<outboundRelationship typeCode="SPRT">
    <#if valueMap['C.2.r.5'] == 1>
    <priorityNumber value="1"/>
            <#if isDebug == 1><!-- C.2.r.5 --></#if>
    </#if>

    <relatedInvestigation classCode="INVSTG" moodCode="EVN">
        <code code="2" codeSystem="2.16.840.1.113883.3.989.2.1.1.22" codeSystemVersion="1.0"
              displayName="sourceReport"/>
        <subjectOf2 typeCode="SUBJ">
            <controlActEvent classCode="CACT" moodCode="EVN">
                <author typeCode="AUT">
                    <assignedEntity classCode="ASSIGNED">
                        <addr>
                            <#if valueMap['C.2.r.2.3']?exists>
                            <streetAddressLine>${valueMap['C.2.r.2.3']?if_exists?xml}</streetAddressLine>
                                    <#if isDebug == 1><!-- C.2.r.2.3 --></#if>
                            <#elseif nullflavorValueMap['C.2.r.2.3']?exists>
                            <streetAddressLine nullFlavor="${nullflavorValueMap['C.2.r.2.3']?xml}"/>
                                    <#if isDebug == 1><!-- C.2.r.2.3 nullflavor --></#if>
                            </#if>
                            <#if valueMap['C.2.r.2.4']?exists>
                            <city>${valueMap['C.2.r.2.4']?if_exists?xml}</city>
                                    <#if isDebug == 1><!-- C.2.r.2.4 --></#if>
                            <#elseif nullflavorValueMap['C.2.r.2.4']?exists>
                            <city nullFlavor="${nullflavorValueMap['C.2.r.2.4']?xml}"/>
                                    <#if isDebug == 1><!-- C.2.r.2.4 nullflavor --></#if>
                            </#if>
                            <#if valueMap['C.2.r.2.5']?exists>
                            <state>${valueMap['C.2.r.2.5']?if_exists?xml}</state>
                                    <#if isDebug == 1><!-- C.2.r.2.5 --></#if>
                            <#elseif nullflavorValueMap['C.2.r.2.5']?exists>
                            <state nullFlavor="${nullflavorValueMap['C.2.r.2.5']?xml}"/>
                                    <#if isDebug == 1><!-- C.2.r.2.5 nullflavor --></#if>
                            </#if>
                            <#if valueMap['C.2.r.2.6']?exists>
                            <postalCode>${valueMap['C.2.r.2.6']?if_exists?xml}</postalCode>
                                    <#if isDebug == 1><!-- C.2.r.2.6 --></#if>
                            <#elseif nullflavorValueMap['C.2.r.2.6']?exists>
                            <postalCode nullFlavor="${nullflavorValueMap['C.2.r.2.6']?xml}"/>
                                    <#if isDebug == 1><!-- C.2.r.2.6 nullflavor --></#if>
                            </#if>
                        </addr>
                        <#if valueMap['C.2.r.2.7']?exists>
                        <telecom value="tel:${valueMap['C.2.r.2.7']?if_exists?xml}"/>
                                <#if isDebug == 1><!-- C.2.r.2.7 --></#if>
                        <#elseif nullflavorValueMap['C.2.r.2.7']?exists>
                            <telecom nullFlavor="${nullflavorValueMap['C.2.r.2.7']?xml}"/>
                                <#if isDebug == 1><!-- C.2.r.2.7 nullflavor --></#if>
                        </#if>

                        <assignedPerson classCode="PSN" determinerCode="INSTANCE">
                            <name>
                            <#if valueMap['C.2.r.1.1']?exists>
                                <prefix>${valueMap['C.2.r.1.1']?if_exists?xml}</prefix>
                                    <#if isDebug == 1><!-- C.2.r.1.1 --></#if>
                            <#elseif nullflavorValueMap['C.2.r.1.1']?exists>
                                <prefix nullFlavor="${nullflavorValueMap['C.2.r.1.1']?if_exists?xml}"/>
                                    <#if isDebug == 1><!-- C.2.r.1.1 nullflavor--></#if>
                            </#if>
                            <#if valueMap['C.2.r.1.2']?exists>
                                <given>${valueMap['C.2.r.1.2']?if_exists?xml}</given>
                                    <#if isDebug == 1><!-- C.2.r.1.2 --></#if>
                            <#elseif nullflavorValueMap['C.2.r.1.2']?exists>
                                <given nullFlavor="${nullflavorValueMap['C.2.r.1.2']?if_exists?xml}"/>
                                    <#if isDebug == 1><!-- C.2.r.1.2 nullflavor--></#if>
                            </#if>
                            <#if valueMap['C.2.r.1.3']?exists>
                                <given>${valueMap['C.2.r.1.3']?if_exists?xml}</given>
                                    <#if isDebug == 1><!-- C.2.r.1.3 --></#if>
                            <#elseif nullflavorValueMap['C.2.r.1.3']?exists>
                                <given nullFlavor="${nullflavorValueMap['C.2.r.1.3']?if_exists?xml}"/>
                                    <#if isDebug == 1><!-- C.2.r.1.3 nullflavor--></#if>
                            </#if>
                            <#if valueMap['C.2.r.1.4']?exists>
                                <family>${valueMap['C.2.r.1.4']?if_exists?xml}</family>
                                    <#if isDebug == 1><!-- C.2.r.1.4 --></#if>
                            <#elseif nullflavorValueMap['C.2.r.1.4']?exists>
                                <family nullFlavor="${nullflavorValueMap['C.2.r.1.4']?if_exists?xml}"/>
                                    <#if isDebug == 1><!-- C.2.r.1.4 nullflavor--></#if>
                            </#if>
                            </name>
<#if valueMap['C.2.r.4']?exists>
                            <asQualifiedEntity classCode="QUAL">
                                <code code="${valueMap['C.2.r.4']?if_exists?xml}"
                                      codeSystem="2.16.840.1.113883.3.989.2.1.1.6" codeSystemVersion="1.0"/>
                                <#if isDebug == 1><!-- C.2.r.4 --></#if>
                            </asQualifiedEntity>
<#elseif nullflavorValueMap['C.2.r.4']?exists>
                            <asQualifiedEntity classCode="QUAL">
                                <code nullFlavor="${nullflavorValueMap['C.2.r.4']?xml}"
                                      codeSystem="2.16.840.1.113883.3.989.2.1.1.6" codeSystemVersion="1.0"/>
                                <#if isDebug == 1><!-- C.2.r.4 nullflavor--></#if>
                            </asQualifiedEntity>
</#if>
<#if valueMap['C.2.r.3']?exists>
                            <asLocatedEntity classCode="LOCE">
                                <location classCode="COUNTRY" determinerCode="INSTANCE">
                                    <code code="${valueMap['C.2.r.3']?if_exists?xml}" codeSystem="1.0.3166.1.2.2"/>
									<#if isDebug == 1><!-- C.2.r.3 --></#if>
                                </location>
                            </asLocatedEntity>
<#elseif nullflavorValueMap['C.2.r.3']?exists>
                            <asLocatedEntity classCode="LOCE">
                                <location classCode="COUNTRY" determinerCode="INSTANCE">
                                    <code nullFlavor="${nullflavorValueMap['C.2.r.3']?xml}" codeSystem="1.0.3166.1.2.2"/>
									<#if isDebug == 1><!-- C.2.r.3 nullflavor--></#if>
                                </location>
                            </asLocatedEntity>
</#if>
                        </assignedPerson>
                        <representedOrganization classCode="ORG" determinerCode="INSTANCE">
                        <#if valueMap['C.2.r.2.2']?exists>
                        <name>${valueMap['C.2.r.2.2']?if_exists?xml}</name>
                                <#if isDebug == 1><!-- C.2.r.2.2 --></#if>
                        <#elseif nullflavorValueMap['C.2.r.2.2']?exists>
                        <name nullFlavor="${nullflavorValueMap['C.2.r.2.2']?xml}"/>
                                <#if isDebug == 1><!-- C.2.r.2.2 nullflavor--></#if>
                        </#if>
<#if valueMap['C.2.r.2.1']?exists>
                            <assignedEntity classCode="ASSIGNED">
                                <representedOrganization classCode="ORG" determinerCode="INSTANCE">
                                    <name>${valueMap['C.2.r.2.1']?if_exists?xml}</name>
									<#if isDebug == 1><!-- C.2.r.2.1 --></#if>
                                </representedOrganization>
                            </assignedEntity>
<#elseif nullflavorValueMap['C.2.r.2.1']?exists>
                            <assignedEntity classCode="ASSIGNED">
                                <representedOrganization classCode="ORG" determinerCode="INSTANCE">
                                    <name nullFlavor="${nullflavorValueMap['C.2.r.2.1']?if_exists?xml}"/>
									<#if isDebug == 1><!-- C.2.r.2.1 nullflavor--></#if>
                                </representedOrganization>
                            </assignedEntity>
</#if>
                        </representedOrganization>
                    </assignedEntity>
                </author>
            </controlActEvent>
        </subjectOf2>
    </relatedInvestigation>
</outboundRelationship>
