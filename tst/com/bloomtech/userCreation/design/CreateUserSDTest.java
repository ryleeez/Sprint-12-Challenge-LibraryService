package com.bloomtech.userCreation.design;

import com.amazon.ata.test.assertions.PlantUmlSequenceDiagramAssertions;
import com.amazon.ata.test.helper.AtaTestHelper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CreateUserSDTest {
    private static final String CREATE_USER_SEQUENCE_DIAGRAM_PATH = "create-user-SD.puml";

    @ParameterizedTest
    @ValueSource(strings = {"UserController", "UserService", "UserDataRepository"})
    void createUserSequenceDiagram_includesExpectedTypes(String type) {
        String content = AtaTestHelper.getFileContentFromResources(CREATE_USER_SEQUENCE_DIAGRAM_PATH);

        PlantUmlSequenceDiagramAssertions.assertSequenceDiagramContainsEntity(content, type);
    }

    @ParameterizedTest
    @ValueSource(strings = {"400 Response", "201 Response"})
    void createUserSequenceDiagram_includesExpectedReturnTypes(String type) {
        String content = AtaTestHelper.getFileContentFromResources(CREATE_USER_SEQUENCE_DIAGRAM_PATH);

        PlantUmlSequenceDiagramAssertions.assertSequenceDiagramContainsReturnType(content, type);
    }

    @ParameterizedTest
    @ValueSource(strings = {"createUser", "validateUser", "validate"})
    void createUserSequenceDiagram_includesExpectedMethodCalls(String method) {
        String content = AtaTestHelper.getFileContentFromResources(CREATE_USER_SEQUENCE_DIAGRAM_PATH);

        PlantUmlSequenceDiagramAssertions.assertSequenceDiagramContainsMethod(content, method);
    }
}
