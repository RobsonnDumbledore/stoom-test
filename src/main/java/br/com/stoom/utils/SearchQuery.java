package br.com.stoom.utils;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {

    public static SearchQuery with(
            int page,
            int perPage,
            String terms,
            String sort,
            String direction
    ) {
        return new SearchQuery(page, perPage, terms, sort, direction);
    }

    public static SearchQuery with(
            int page,
            int perPage,
            String sort,
            String direction
    ) {
        return SearchQuery.with(page, perPage, null, sort, direction);
    }

    public static SearchQuery with(
            int page,
            int perPage
    ) {
        return SearchQuery.with(page, perPage, null, null, null);
    }

}
