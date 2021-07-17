create table NOTE
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR,
    content     TEXT,
    sort_idx    BIGINT,
    parent_id   VARCHAR,
    category_id BIGINT
)