import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public class DesafioCadastroComSucesso {
	
	private WebDriver driver;
	
	@Before
	public void inicializa() {
		driver = new FirefoxDriver();
		driver.manage().window().setSize(new Dimension(1200, 765));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
	}
	
	@After
	public void finaliza() {
		driver.close();
	}

	@Test
	public void CadastroSucesso() {
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Sávio");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Borges");
		driver.findElement(By.id("elementosForm:sexo:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:2")).click();
		
		WebElement element = driver.findElement(By.id("elementosForm:escolaridade"));
		Select combo = new Select(element);
		combo.selectByVisibleText("Superior");
		
		element = driver.findElement(By.id("elementosForm:esportes"));
		combo = new Select(element);
		combo.selectByVisibleText("Natacao");
		
		driver.findElement(By.id("elementosForm:sugestoes")).sendKeys("Ao invés de sugestão, mande um pix!");
		
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		
		//Verificações
		Assert.assertEquals("Cadastrado!", driver.findElement(By.cssSelector("#resultado span")).getText());
		
		Assert.assertEquals("Sávio", driver.findElement(By.cssSelector("#descNome span")).getText());
		Assert.assertEquals("Borges", driver.findElement(By.cssSelector("#descSobrenome span")).getText());
		Assert.assertEquals("Masculino", driver.findElement(By.cssSelector("#descSexo span")).getText());
		Assert.assertEquals("Pizza", driver.findElement(By.cssSelector("#descComida span")).getText());
		Assert.assertEquals("superior", driver.findElement(By.cssSelector("#descEscolaridade span")).getText());
		Assert.assertEquals("Natacao", driver.findElement(By.cssSelector("#descEsportes span")).getText());
		Assert.assertEquals("Ao invés de sugestão, mande um pix!", driver.findElement(By.cssSelector("#descSugestoes span")).getText());	
	}
	
	@Test
	public void ValidarNome() {
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Nome eh obrigatorio", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarSobrenome() {
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Sávio");
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sobrenome eh obrigatorio", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarSexo() {
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Sávio");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Borges");
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sexo eh obrigatorio", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarComidaVegetariana() {
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Sávio");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Borges");
		driver.findElement(By.id("elementosForm:sexo:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:3")).click();
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Tem certeza que voce eh vegetariano?", alert.getText());
		
		alert.accept();
	}
	
	@Test
	public void ValidarEsporte() {
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Sávio");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Borges");
		driver.findElement(By.id("elementosForm:sexo:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
		Select combo = new Select(driver.findElement(By.id("elementosForm:esportes")));
		combo.selectByVisibleText("Natacao");
		combo.selectByVisibleText("O que eh esporte?");
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Voce faz esporte ou nao?", alert.getText());
		
		alert.accept();
	}
}
