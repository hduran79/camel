package co.com.votre.camel.repository.product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import co.com.votre.camel.dto.product.CampaingDTO;
import co.com.votre.camel.dto.product.MessageDTO;

@Mapper
public interface IProductRepository {

    MessageDTO getCampaignRank(@Param("company") String company, @Param("media") String media);

    MessageDTO postCampaignRank(CampaingDTO dto);

}
