package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Album;
import com.mycompany.myapp.domain.Tag;
import com.mycompany.myapp.service.dto.AlbumDTO;
import com.mycompany.myapp.service.dto.TagDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    @Mapping(target = "albums", source = "albums", qualifiedByName = "albumIdSet")
    TagDTO toDto(Tag s);

    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "removeAlbums", ignore = true)
    Tag toEntity(TagDTO tagDTO);

    @Named("albumId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlbumDTO toDtoAlbumId(Album album);

    @Named("albumIdSet")
    default Set<AlbumDTO> toDtoAlbumIdSet(Set<Album> album) {
        return album.stream().map(this::toDtoAlbumId).collect(Collectors.toSet());
    }
}
