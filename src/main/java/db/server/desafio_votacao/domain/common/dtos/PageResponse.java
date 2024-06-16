package db.server.desafio_votacao.domain.common.dtos;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response for paginated resources.
 * 
 * @param <T> type for returned items in the page.
 * @author Caio Porcel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
	private boolean hasNextPage;
	private boolean hasPreviousPage;
	private int page;
	private int size;
	private List<T> items;

	public PageResponse(Page<T> page) {
		this.hasNextPage = page.hasNext();
		this.hasPreviousPage = page.hasPrevious();
		this.page = page.getNumber();
		this.size = page.getSize();
		this.items = page.getContent();
	}
}
