package rz.topic.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CatalogPageQueryDTO extends PageQueryDTO {

	private Long topicId;

}
