package com.fragansias.company.service.implementaciones;

import com.fragansias.company.models.entity.ItemFactura;
import com.fragansias.company.repository.ItemFacturaRepository;
import com.fragansias.company.service.contrato.ItemFacturaDAO;
import org.springframework.stereotype.Service;

@Service
public class ItemFacturaImp extends GenericoDAOImpl<ItemFactura, ItemFacturaRepository> implements ItemFacturaDAO {
    public ItemFacturaImp(ItemFacturaRepository repository) {
        super(repository);
    }
}
