package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AlbumAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAlbumAllPropertiesEquals(Album expected, Album actual) {
        assertAlbumAutoGeneratedPropertiesEquals(expected, actual);
        assertAlbumAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAlbumAllUpdatablePropertiesEquals(Album expected, Album actual) {
        assertAlbumUpdatableFieldsEquals(expected, actual);
        assertAlbumUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAlbumAutoGeneratedPropertiesEquals(Album expected, Album actual) {
        assertThat(actual)
            .as("Verify Album auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAlbumUpdatableFieldsEquals(Album expected, Album actual) {
        assertThat(actual)
            .as("Verify Album relevant properties")
            .satisfies(a -> assertThat(a.getName()).as("check name").isEqualTo(expected.getName()))
            .satisfies(a -> assertThat(a.getEvent()).as("check event").isEqualTo(expected.getEvent()))
            .satisfies(a -> assertThat(a.getCreationDate()).as("check creationDate").isEqualTo(expected.getCreationDate()))
            .satisfies(a -> assertThat(a.getOverrideDate()).as("check overrideDate").isEqualTo(expected.getOverrideDate()))
            .satisfies(a -> assertThat(a.getThumbnail()).as("check thumbnail").isEqualTo(expected.getThumbnail()))
            .satisfies(a ->
                assertThat(a.getThumbnailContentType()).as("check thumbnail contenty type").isEqualTo(expected.getThumbnailContentType())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAlbumUpdatableRelationshipsEquals(Album expected, Album actual) {
        // empty method
    }
}
