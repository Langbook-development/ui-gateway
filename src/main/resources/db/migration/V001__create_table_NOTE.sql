create table NOTE
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR   NOT NULL,
    content     TEXT      NOT NULL,
    sort_idx    BIGINT    NOT NULL,
    parent_id   BIGINT,
    category_id BIGINT    NOT NULL
)