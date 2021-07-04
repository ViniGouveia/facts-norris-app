<h1 align="start">Facts Norris</h1>


[![Android Pull Request CI Configuration](https://github.com/ViniGouveia/facts-norris-app/actions/workflows/pr-ci-config.yml/badge.svg)](https://github.com/ViniGouveia/facts-norris-app/actions/workflows/pr-ci-config.yml)
[![codecov](https://codecov.io/gh/ViniGouveia/facts-norris-app/branch/master/graph/badge.svg?token=4XAK9KX7DV)](https://codecov.io/gh/ViniGouveia/facts-norris-app)

<h1 align="start">Sobre</h1>

O objetivo da aplicação é listar fatos sobre Chuck Norris, com opção de filtrar a lista através da feature de busca, podendo também compartilhar o fato, clicando nele. Exemplos abaixo.

O projeto foi um pouco desafiador, pois nunca tinha trabalhado com a arquitetura MVVM, kotlin coroutines e outras ferramentas utilizadas, mas acredito que tenha tomado as melhoes decisões. Não tenho muito conhecimento sobre testes instrumentados, mas é um desafio que decidi abraçar pros próximos projetos. Não tenho muita habilidade com UI/UX, mas tentei deixar o mais agradável possível.

<h1 align="start">Como executar</h1>
<p align="start">A execução do projeto é simples, instale o Android Studio na sua máquina e siga os passos:</p>

```
- Através do terminal, clone este repositório;
$ git clone https://github.com/ViniGouveia/facts-norris-app.git

- Abra o projeto no Android Studio;

- Aguarde a indexação do projeto (esse processo pode demorar alguns minutos, dependendo da máquina e da internet);

- Por fim, execute o Run do projeto em um dispositivo físico ou virtual.
```

<h1 align="start">Explicação da arquitetura</h1>

Procurei focar no funcionamento e na manutenção do projeto e tentei o máximo possível seguir os pricípios SOLID e da Clean Architecture.

<p align="middle">
    <img src="./resources/architecture.png">
    <p style="text-align:center"><i>Essa é uma representação da arquitetura utilizada no projeto. Entre a classe Repository e a ViewModel existe a camada UseCase, responsável pelas regras de negócio e faz comunicação com o Repository ou com outros UseCases.</i></p>
    <p style="text-align:center"><i>As flechas podem ser traduzidas como "fala com"</i></p>
</p>

<h1 align="start">Features</h1>

<p align="middle">
    <img src="./resources/first_access.png">
    <p style="text-align:center">Essa é uma representação da primeira tela que o usuário irá ver ao entrar na aplicação.</p>
</p>

<p align="middle">
    <img src="./resources/facts_listed.png">
    <p style="text-align:center">Após realizar alguma busca, os fatos serão carregados e exibidos como no exemplo acima.</p>
</p>

<p align="middle">
    <img src="./resources/search_screen.png">
    <p style="text-align:center">Essa é a tela onde são feitas as buscas no app. São apresentadas 8 sugestões aleatórias cada vez que a tela é exibida, também é exibida as últimas buscas feitas pelo usuário. É possível voltar a tela anterior clicando no botão "back".</p>
</p>

<h1 align="start">Continuous Integration</h1>

O projeto utiliza Github Actions para executar os scripts de CI quando é aberto um Pull Request ou quando uma Tag é criada.

### Pull Request CI

O script roda em duas etapas:
    - Análise estática do código: O script utiliza o plugin detekt para fazer uma análise do código, verificando se está tudo formatado consistentemente e se não possui algum erro na complexidade.
    - Cobertura de código: o JUnit5 é responsável por executar os testes unitários e instrumentados, o reporte é feito pelo Jacoco e o Codecov.io checa a cobertura de código e se está de acordo com o esperado.

### Tag CI

O script possui apenas uma etapa, que consiste em gerar um debug APK e da upload nele como um artefato do Github Actions.
