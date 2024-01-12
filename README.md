# Clínica Ágil 👩🏻‍⚕️

Este é o projeto de uma Clínica de Consultas Ágil, feito para o processo seletivo da Aceleradora Ágil.

## Requisitos

- Java (JDK) versão 20 (ou superior)
- Biblioteca Jackson para manipulação de JSON

## Funcionalidades

- **Cadastrar Paciente:** Registra um novo paciente na clínica.
- **Marcar Consulta:** Agenda uma consulta para um paciente específico.
- **Cancelar Consulta:** Remove uma consulta previamente agendada.
- **Salvar Dados em JSON:** Salva os dados atuais (pacientes e consultas) em arquivos JSON(essa função só está disponível no arquivo `ClinicaDeConsultasAgilComSalvamentoEmJson`).
- **Sair:** O programa encerra a execução.

## Estrutura do Projeto

- `ClinicaDeConsultasAgil.java`: O arquivo que contém a lógica do programa SEM o salvamento de dados.
- `ClinicaDeConsultasAgilComSalvamentoEmJson.java`: O arquivo que contém a lógica do programa COM o salvamento de dados.

## Como executar o Projeto SEM salvamento de dados:

1. Baixe o arquivo .zip do repositório;
2. Compile o projeto;
3. Execute o programa através do arquivo `ClinicaDeConsultasAgil.java`.

## Como executar o Projeto COM salvamento de dados:

1. Baixe o arquivo .zip do repositório;
2. Baixe o JAR do Jackson [aqui](https://jar-download.com/artifacts/com.fasterxml.jackson.core);
3. Compile o projeto incluindo o JAR no classpath;
4. Execute o programa através do arquivo `ClinicaDeConsultasAgilComSalvamentoEmJson.java`.

