# View Materializada

Utilizando o conceito de view materializada para otimizar consultas complexas (CQRS pattern).

![](https://miro.medium.com/v2/resize:fit:720/format:webp/1*TaPzEj91HM06UgZoajqGwA.png)

### Passo 1 - Rodando a aplicação

```bash
# subir container do banco de dados (docker)
docker-compose up -d

# subir microserviço (vai subir a carga automaticamente)
./gradlew bootRun
```

### Passo 2 - Instalação do Apache Benchmark (ab)

```bash
sudo apt-get install apache2-utils
```
Site: https://httpd.apache.org/docs/2.4/programs/ab.html

### Passo 3 - Rodar o teste de carga

Volumentria de 100.000 registros na tabela principal

#### 1 - Teste com query normal (multiplos joins)

```bash
# Executar 100 vezez com 2 requests em paralelo
ab -n 100 -c 2 http://localhost:8080/query-with-tables

#	Concurrency Level:      2
#	Time taken for tests:   2.212 seconds
#	Complete requests:      100
#	Failed requests:        0
#	Total transferred:      97000 bytes
#	HTML transferred:       86500 bytes
#	Requests per second:    45.20 [#/sec] (mean)
#	Time per request:       44.248 [ms] (mean)
#	Time per request:       22.124 [ms] (mean, across all concurrent requests)
#	Transfer rate:          42.82 [Kbytes/sec] received
```
#### 2 - Teste com a view materializada
```bash
# Executar 100 vezez com 2 requests em paralelo
ab -n 100 -c 2 http://localhost:8080/query-with-view

#	Concurrency Level:      2
#	Time taken for tests:   0.122 seconds
#	Complete requests:      100
#	Failed requests:        0
#	Total transferred:      107800 bytes
#	HTML transferred:       97300 bytes
#	Requests per second:    821.51 [#/sec] (mean)
#	Time per request:       2.435 [ms] (mean)
#	Time per request:       1.217 [ms] (mean, across all concurrent requests)
#	Transfer rate:          864.83 [Kbytes/sec] received
```
Observe que o resultado da view materializada é muito mais rápido que a consulta normal.

### Passo 4 - Informações complementares

#### Diagrama (DER)

| Table          | Rows    |
|----------------|---------|
| users          | 10.000  |
| product        | 1.000   |
| purchase_order | 100.000 |

![img.png](src/test/resources/img/img.png)

#### Query

```sql
select
    u.state,
    sum(p.price) as total_sale
from
    users u,
    product p,
    purchase_order po
where
    u.id = po.user_id
    and p.id = po.product_id
```

### Passo 5 - Conclusão

O uso de view materializada é uma técnica muito eficiente para otimizar consultas complexas, principalmente em sistemas que possuem muitos dados. A view materializada é uma tabela que armazena o resultado de uma consulta, permitindo que a aplicação consulte essa tabela em vez de executar a consulta original. Isso pode melhorar significativamente o desempenho da aplicação, especialmente em consultas que envolvem muitas tabelas ou operações complexas.
