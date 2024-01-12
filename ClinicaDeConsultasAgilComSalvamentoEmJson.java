package ClinicaAgil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.LocalDate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClinicaDeConsultasAgilComSalvamentoEmJson {

	public static class Paciente {
		String nome;
		String telefone;

		public Paciente() {

		}

		public Paciente(String nome, String telefone) {
			this.nome = nome;
			this.telefone = telefone;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getTelefone() {
			return telefone;
		}

		public void setTelefone(String telefone) {
			this.telefone = telefone;
		}

		@Override
		public String toString() {
			return "Nome: " + nome + ", Telefone: " + telefone;
		}
	}

	public static class Consulta {
		int dia;
		int hora;
		String especialidade;
		Paciente paciente;

		public Consulta() {

		}

		public Consulta(int dia, int hora, String especialidade, Paciente paciente) {
			this.dia = dia;
			this.hora = hora;
			this.especialidade = especialidade;
			this.paciente = paciente;
		}

		public int getDia() {
			return dia;
		}

		public void setDia(int dia) {
			this.dia = dia;
		}

		public int getHora() {
			return hora;
		}

		public void setHora(int hora) {
			this.hora = hora;
		}

		public String getEspecialidade() {
			return especialidade;
		}

		public void setEspecialidade(String especialidade) {
			this.especialidade = especialidade;
		}

		public Paciente getPaciente() {
			return paciente;
		}

		public void setPaciente(Paciente paciente) {
			this.paciente = paciente;
		}

		@Override
		public String toString() {
			return "Paciente: " + paciente.nome + ", Hora: " + hora + ", Dia: " + dia + ", Especialidade: "
					+ especialidade;
		}
	}

	public boolean pacienteJaCadastrado(String telefone) {
		for (Paciente paciente : listaPacientes) {
			if (paciente.telefone.equals(telefone)) {
				return true;
			}
		}
		return false;
	}

	public boolean horarioOcupado(int dia, int hora) {
		for (Consulta consulta : listaConsultas) {
			if (consulta.dia == dia && consulta.hora == hora) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<Paciente> listaPacientes = new ArrayList<>();
	private static ArrayList<Consulta> listaConsultas = new ArrayList<>();

	public void adicionarPaciente(Paciente paciente) {
		listaPacientes.add(paciente);
	}

	public void marcarConsulta(Consulta consulta) {
		listaConsultas.add(consulta);
	}

	public void cancelarConsulta(Consulta consulta) {
		listaConsultas.remove(consulta);
	}

	public void salvarDadosEmJSON() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			objectMapper.writeValue(new File("pacientes.json"), listaPacientes);

			objectMapper.writeValue(new File("consultas.json"), listaConsultas);

			System.out.println("Dados salvos em JSON com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao salvar os dados em JSON: " + e.getMessage());
		}
	}

	public void carregarDadosDeJSON() {
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			List<Paciente> pacientesLidos = objectMapper.readValue(new File("pacientes.json"),
					new TypeReference<List<Paciente>>() {
					});
			listaPacientes = new ArrayList<>(pacientesLidos);

			List<Consulta> consultasLidas = objectMapper.readValue(new File("consultas.json"),
					new TypeReference<List<Consulta>>() {
					});
			listaConsultas = new ArrayList<>(consultasLidas);

			System.out.println("Dados carregados de JSON com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao carregar os dados de JSON: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);

		ClinicaDeConsultasAgilComSalvamentoEmJson clinica = new ClinicaDeConsultasAgilComSalvamentoEmJson();

		clinica.carregarDadosDeJSON();

		while (true) {

			System.out.println("Menu");
			System.out.println("1-Cadastrar paciente");
			System.out.println("2-Marcar consulta");
			System.out.println("3-Cancelar consulta");
			System.out.println("4-Salvar dados em Json");
			System.out.println("0-Sair");

			int opcaoEscolhida = teclado.nextInt();

			switch (opcaoEscolhida) {

			case 1:
				System.out.println("Cadastro de paciente");
				System.out.println("Informe o nome do paciente:");

				String nome = teclado.next();

				String telefone;
				while (true) {
					System.out.println("Informe o telefone do paciente:");
					telefone = teclado.next();

					if (telefone.length() == 13) {
						break;
					} else {
						System.out.println("Telefone inválido! Formato de telefone válido: (99)999999999");
					}
				}

				if (clinica.pacienteJaCadastrado(telefone)) {
					System.out.println("Paciente já cadastrado!");
				} else {

					Paciente novoPaciente = new Paciente(nome, telefone);

					clinica.adicionarPaciente(novoPaciente);

					System.out.println("Paciente cadastrado com sucesso!");

				}
				break;

			case 2:
				System.out.println("Agendamento de consultas");

				System.out.println("Lista de pacientes:");
				for (int i = 0; i < clinica.listaPacientes.size(); i++) {
					System.out.println(i + ". " + clinica.listaPacientes.get(i));
				}

				System.out.println("Informe o número do paciente para a consulta:");
				int paciente = teclado.nextInt();

				if (paciente >= 0 && paciente < clinica.listaPacientes.size()) {

					ClinicaDeConsultasAgilComSalvamentoEmJson.Paciente pacienteSelecionado = clinica.listaPacientes
							.get(paciente);

					System.out.println("Informe o dia (apenas números):");
					int dia = teclado.nextInt();

					if (dia < 1 || dia > 30) {
						System.out.println("Dia inválido. Informe um dia entre 1 e 30.");
					} else {

						if (LocalDate.now().getDayOfMonth() > dia) {
							System.out.println("Não é possível marcar consultas retroativas.");
						} else {

							System.out.println("Informe a hora (apenas números):");
							int hora = teclado.nextInt();

							if (hora < 0 || hora > 23) {
								System.out.println("Hora inválida. Informe uma hora entre 0 e 23.");
							} else {

								if (clinica.horarioOcupado(dia, hora)) {
									System.out.println("Horário já ocupado. Escolha outro dia ou hora.");
								} else {

									System.out.println("Informe a especialidade:");
									String especialidade = teclado.next();

									Consulta novaConsulta = new Consulta(dia, hora, especialidade, pacienteSelecionado);

									clinica.marcarConsulta(novaConsulta);

									System.out.println("Consulta agendada!");

								}
							}
						}
					}

					break;

				} else {
					System.out.println(
							"Paciente inválido. Informe apenas o número que aparece antes do nome do paciente. Tente Novamente!");
					break;

				}

			case 3:
				System.out.println("Cancelamento de consultas");

				System.out.println("Lista de consultas agendadas:");
				for (int i = 0; i < ClinicaDeConsultasAgilComSalvamentoEmJson.listaConsultas.size(); i++) {
					System.out.println(i + ". " + ClinicaDeConsultasAgilComSalvamentoEmJson.listaConsultas.get(i));
				}

				System.out.println("Informe o número da consulta a ser cancelada:");
				int consulta = teclado.nextInt();

				if (consulta >= 0 && consulta < ClinicaDeConsultasAgilComSalvamentoEmJson.listaConsultas.size()) {

					ClinicaDeConsultasAgilComSalvamentoEmJson.Consulta consultaSelecionada = ClinicaDeConsultasAgilComSalvamentoEmJson.listaConsultas
							.get(consulta);

					System.out.println(consultaSelecionada);

					System.out.println("Deseja cancelar essa consulta?");
					System.out.println("1. Sim");
					System.out.println("2. Não");
					int escolha = teclado.nextInt();

					if (escolha == 1) {
						listaConsultas.remove(consultaSelecionada);
						System.out.println("Consulta cancelada!");
						break;
					} else {
						break;
					}

				} else {
					System.out.println("Número de consulta inválido! Tente novamente.");
					break;

				}

			case 4:

				System.out.println("Salvando dados em Json...");

				clinica.salvarDadosEmJSON();

				break;

			case 0:
				System.out.println("Saindo do programa...");
				teclado.close();
				System.exit(0);
				break;

			default:
				System.out.println("Opção inválida. Tente novamente.");
				break;
			}
		}
	}

}
