package org.mickey.framework.filemanager.api;

import org.junit.Before;
import org.junit.Test;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.filemanager.dto.PolicyRequestDto;
import org.mickey.framework.filemanager.dto.PolicyResultDto;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * description
 *
 * @author mickey
 * 2020-02-12
 */
public class FileCredentialControllerTest extends BaseSpringTest {

    @Autowired
    private FileCredentialController credentialController;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getPolicy() {
        PolicyRequestDto requestDto = new PolicyRequestDto();
        requestDto.setAction("PutObject");
        ActionResult<PolicyResultDto> policy = credentialController.getPolicy(requestDto);

        print(policy);
    }
}