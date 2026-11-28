

## 0.3
- V1 was empty and the local DB was baselined at v1, so it never ran. Rebuilt it from a schema-only pg_dump and stripped the psql-only and OWNER TO lines.

## 0.4
- Seeded problems as V2 from a data-only pg_dump with --rows-per-insert and --on-conflict-do-nothing, so the same file fills an empty DB and is a no-op on an existing one.
- Local recall DB had V1 recorded with checksum 0 (the old empty V1). Realigned it in flyway_schema_history by hand, which is what flyway repair does. Local only, never on a server.
