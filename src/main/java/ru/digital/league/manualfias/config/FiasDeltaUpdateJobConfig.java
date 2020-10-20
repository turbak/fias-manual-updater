package ru.digital.league.manualfias.config;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.digital.league.manualfias.batch.reader.AddressItemReader;
import ru.digital.league.manualfias.batch.reader.HouseItemReader;
import ru.digital.league.manualfias.db.entity.AddressEntity;
import ru.digital.league.manualfias.db.entity.HouseEntity;
import ru.digital.league.manualfias.xml.pojo.AddressObject;
import ru.digital.league.manualfias.xml.pojo.House;

import javax.persistence.EntityManagerFactory;

@Configuration
public class FiasDeltaUpdateJobConfig {

	@Bean
	public Job fiasUpdateJob(JobBuilderFactory jobBuilderFactory, Step saveAddressXmlDataStep, Step saveHouseXmlDataStep) {
		return jobBuilderFactory.get("fiasUpdateJob")
				.start(saveAddressXmlDataStep)
				.next(saveHouseXmlDataStep)
				.build();
	}

	@Bean
	public Step saveAddressXmlDataStep(StepBuilderFactory stepBuilderFactory,
									   ItemReader<AddressObject> addressObjectItemReader,
									   ItemProcessor<AddressObject, AddressEntity> addressItemProcessor,
									   ItemWriter<AddressEntity> addressObjectItemWriter,
									   ChunkListener chunkLoggerListener) {
		return stepBuilderFactory.get("saveAddressXmlDataStep")
				.<AddressObject, AddressEntity>chunk(500)
				.reader(addressObjectItemReader)
				.processor(addressItemProcessor)
				.writer(addressObjectItemWriter)
				.listener(chunkLoggerListener)
				.build();
	}

	@Bean
	public Step saveHouseXmlDataStep(StepBuilderFactory stepBuilderFactory,
									 ItemReader<House> houseItemReader,
									 ItemProcessor<House, HouseEntity> houseEntityItemProcessor,
									 ItemWriter<HouseEntity> houseItemWriter,
									 ChunkListener chunkLoggerListener) {
		return stepBuilderFactory.get("saveHouseXmlDataStep")
				.<House, HouseEntity>chunk(500)
				.reader(houseItemReader)
				.processor(houseEntityItemProcessor)
				.writer(houseItemWriter)
				.listener(chunkLoggerListener)
				.build();
	}

	@Bean
	public ItemReader<AddressObject> addressObjectItemReader(Jaxb2Marshaller jaxb2Marshaller) {
		AddressItemReader addressItemReader = new AddressItemReader();
		addressItemReader.setFragmentRootElementName("Object");
		addressItemReader.setUnmarshaller(jaxb2Marshaller);
		return addressItemReader;
	}

	@Bean
	public ItemWriter<AddressEntity> addressObjectItemWriter(EntityManagerFactory entityManagerFactory) {
		JpaItemWriter<AddressEntity> addressItemWriter = new JpaItemWriter<>();
		addressItemWriter.setEntityManagerFactory(entityManagerFactory);
		return addressItemWriter;
	}

	@Bean
	public ItemReader<House> houseItemReader(Jaxb2Marshaller jaxb2Marshaller) {
		HouseItemReader houseItemReader = new HouseItemReader();
		houseItemReader.setFragmentRootElementName("House");
		houseItemReader.setUnmarshaller(jaxb2Marshaller);
		return houseItemReader;
	}

	@Bean
	public ItemWriter<HouseEntity> houseItemWriter(EntityManagerFactory entityManagerFactory) {
		JpaItemWriter<HouseEntity> houseItemWriter = new JpaItemWriter<>();
		houseItemWriter.setEntityManagerFactory(entityManagerFactory);
		return houseItemWriter;
	}

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(AddressObject.class, House.class);
		return jaxb2Marshaller;
	}

}
