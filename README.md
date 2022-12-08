# Curso de testes da udemy
Link: https://www.udemy.com/course/testes-com-junit-5-mockito-e-spring-boot-rest-apis/

Autor: Valdir Cezar

Curso realizado para aprimorar meus conhecimentos em testes unitários utilizando o Junit e Mockito.

Testando branches para prática.

### Princípais anotações e funções:

- @BeforeEach:  Executa antes de cada teste determinados métodos

- @InjectMocks: Cria uma instância real para acesso de métodos e simula outras, o @Mock apenas simula outras como DB, aws etc.

- OpenMocks: inicializa os mocks da classe e espera um objeto.

***MOCKAR RESPOSTAS*** : você simula algum tipo de resposta utilizando mock, exemplo BD

Quando for rodar cobertura, rode o java main dos testes que você obtem o coverage.

**Busque renomear as classes com suas respectivas funções e retornos**

Classes de domínio / entidades geralmente não são testadas, pois você utiliza os métodos pra testar,
mas também é possivel realizar tais testes.

