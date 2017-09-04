package br.com.security.wrapper;

import java.util.Iterator;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPage<T> implements Page<T> {

	private int totalElements;
	private Pageable pageable;
	private List<T> elements;
	private int totalPages;

	public CustomPage(int totalElements, List<T> elements, Pageable pageable) {
		this.totalElements = totalElements;
		this.elements = elements;
		this.pageable = pageable;

		totalPages = (totalElements / getSize()) + ((totalElements % getSize() != 0) ? 1 : 0);
	}

	@Override
	public int getNumber() {
		return pageable.getPageNumber();
	}

	@Override
	public int getSize() {
		return pageable.getPageSize();
	}

	@Override
	public int getNumberOfElements() {
		return elements.size();
	}

	@Override
	public List<T> getContent() {
		return elements;
	}

	@Override
	public boolean hasContent() {
		return elements.size() > 0;
	}

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public boolean isFirst() {
		return 0 == pageable.getPageNumber();
	}

	@Override
	public boolean isLast() {
		return totalPages == pageable.getPageNumber();
	}

	@Override
	public boolean hasNext() {
		return (pageable.getPageNumber() + 1) < totalPages;
	}

	@Override
	public boolean hasPrevious() {
		return pageable.getPageNumber() > 0 && totalPages > 0;
	}

	@Override
	public Pageable nextPageable() {
		return pageable.next();
	}

	@Override
	public Pageable previousPageable() {
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return elements.iterator();
	}

	@Override
	public int getTotalPages() {
		return totalPages;
	}

	@Override
	public long getTotalElements() {
		return totalElements;
	}

	@Override
	public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
		return null;
	}

}
