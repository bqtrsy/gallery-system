package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TagRepository;
import com.mycompany.myapp.service.TagService;
import com.mycompany.myapp.service.dto.TagDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Tag}.
 */
@RestController
@RequestMapping("/api/tags")
public class TagResource {

    private static final Logger LOG = LoggerFactory.getLogger(TagResource.class);
    private static final String ENTITY_NAME = "tag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagService tagService;
    private final TagRepository tagRepository;

    public TagResource(TagService tagService, TagRepository tagRepository) {
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }

    /**
     * {@code POST  /tags} : Create a new tag.
     *
     * @param tagDTO the tagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagDTO, or with status {@code 400 (Bad Request)} if the tag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody TagDTO tagDTO) throws URISyntaxException {
        LOG.debug("REST request to save Tag : {}", tagDTO);
        if (tagDTO.getId() != null) {
            LOG.warn("Invalid tag creation attempt with existing ID: {}", tagDTO.getId());
            throw new BadRequestAlertException("A new tag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            tagDTO = tagService.save(tagDTO);
            LOG.info("Tag created successfully with ID: {}", tagDTO.getId());
            return ResponseEntity.created(new URI("/api/tags/" + tagDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tagDTO.getId().toString()))
                .body(tagDTO);
        } catch (Exception e) {
            LOG.error("Failed to create tag: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * {@code PUT  /tags/:id} : Updates an existing tag.
     *
     * @param id the id of the tagDTO to save.
     * @param tagDTO the tagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagDTO,
     * or with status {@code 400 (Bad Request)} if the tagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody TagDTO tagDTO)
        throws URISyntaxException {
        LOG.debug("REST request to update Tag : {}, {}", id, tagDTO);
        if (tagDTO.getId() == null) {
            LOG.warn("Invalid tag update attempt with null ID");
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tagDTO.getId())) {
            LOG.warn("Invalid tag update attempt with mismatched IDs. Path ID: {}, DTO ID: {}", id, tagDTO.getId());
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!tagRepository.existsById(id)) {
            LOG.warn("Attempt to update non-existent tag with ID: {}", id);
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        try {
            tagDTO = tagService.update(tagDTO);
            LOG.info("Tag updated successfully. ID: {}", tagDTO.getId());
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tagDTO.getId().toString()))
                .body(tagDTO);
        } catch (Exception e) {
            LOG.error("Failed to update tag with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * {@code PATCH  /tags/:id} : Partial updates given fields of an existing tag, field will ignore if it is null
     *
     * @param id the id of the tagDTO to save.
     * @param tagDTO the tagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagDTO,
     * or with status {@code 400 (Bad Request)} if the tagDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tagDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TagDTO> partialUpdateTag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TagDTO tagDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Tag partially : {}, {}", id, tagDTO);
        if (tagDTO.getId() == null) {
            LOG.warn("Invalid tag partial update attempt with null ID");
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tagDTO.getId())) {
            LOG.warn("Invalid tag partial update attempt with mismatched IDs. Path ID: {}, DTO ID: {}", id, tagDTO.getId());
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!tagRepository.existsById(id)) {
            LOG.warn("Attempt to partially update non-existent tag with ID: {}", id);
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        try {
            Optional<TagDTO> result = tagService.partialUpdate(tagDTO);
            if (result.isPresent()) {
                LOG.info("Tag partially updated successfully. ID: {}", result.get().getId());
            }
            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tagDTO.getId().toString())
            );
        } catch (Exception e) {
            LOG.error("Failed to partially update tag with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * {@code GET  /tags} : get all the tags.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tags in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TagDTO>> getAllTags(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Tags");
        try {
            Page<TagDTO> page = tagService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            LOG.error("Failed to retrieve tags: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * {@code GET  /tags/:id} : get the "id" tag.
     *
     * @param id the id of the tagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTag(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Tag : {}", id);
        try {
            Optional<TagDTO> tagDTO = tagService.findOne(id);
            if (tagDTO.isEmpty()) {
                LOG.info("Tag not found with ID: {}", id);
            }
            return ResponseUtil.wrapOrNotFound(tagDTO);
        } catch (Exception e) {
            LOG.error("Failed to retrieve tag with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * {@code DELETE  /tags/:id} : delete the "id" tag.
     *
     * @param id the id of the tagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Tag : {}", id);
        try {
            tagService.delete(id);
            LOG.info("Tag deleted successfully. ID: {}", id);
            return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
        } catch (Exception e) {
            LOG.error("Failed to delete tag with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
