package com.fragansias.company.repository;

import com.fragansias.company.models.entity.Producto;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto, Long> {
    @Query(value = "select p From Producto p where upper(p.codigoProducto) = upper(?1) ")
    Iterable<Producto> busquedaPorCodigoProducto (String filtro);
    @Query(value = "select p from Producto p join fetch p.categoria c where upper(c.nombreCategoria) = upper(?1)")
   // @Query(value = "select * from Productos p inner join Categorias c ON p.categoria_id = c.id where upper(c.nombre_categoria) = upper(:param1)"
     //      ,nativeQuery = true)

    Iterable<Producto> busquedaPorCategoria(String param1);
    @Query("select p from Producto p where upper(p.nombreProducto) like upper(%?1%)")
    Iterable <Producto> busquedaPorNombreDeProducto(String name);

}
