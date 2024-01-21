plugins {
	java
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.flywaydb.flyway") version "9.22.0"
}

group = "br.com.stoom.store"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.2.1")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-cache:3.2.1")
	implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")

	// JUnit 5
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	testImplementation("org.flywaydb:flyway-core")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	manifest {
		attributes["Start-Class"] = "br.com.stoom.StoreApplication"
	}
}



tasks.withType<Test> {
	useJUnitPlatform()
}

flyway {

	url = (System.getenv("FLYWAY_DB") ?: "jdbc:postgresql://ec2-54-234-13-16.compute-1.amazonaws.com:5432/dbjch7amri1ncc?currentSchema=public")
	user = (System.getenv("FLYWAY_USER") ?: "djqydhtqcklhnh")
	password = (System.getenv("FLYWAY_PASS") ?: "455d35ecff8b256bc509cdd61329e54d438365fe209098f7a61f8afa7b56829b")
	cleanDisabled = false

}
