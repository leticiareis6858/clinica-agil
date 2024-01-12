package ClinicaAgil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ClinicaDeConsultasAgil {

	public class Paciente {
		String nome;
		String telefone;

		@Override
		public String toString() {
			return "Nome: " + nome + ", Telefone: " + telefone;
		}

	}

	public class Consulta {
		int dia;
		int hora;
		String especialidade;
		Paciente paciente;

		public Consulta(int dia, int hora, String especialidade, Paciente paciente) {
			this.dia = dia;
			this.hora = hora;
			this.especialidade = especialidade;
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

	ArrayList<Paciente> listaPacientes = new ArrayList<Paciente>();

	static ArrayList<Consulta> listaConsultas = new ArrayList<Consulta>();

	public void adicionarPaciente(Paciente paciente) {
		listaPacientes.add(paciente);
	}

	public void marcarConsulta(Consulta consulta) {
		listaConsultas.add(consulta);
	}

	public void cancelarConsulta(Consulta consulta) {
		listaConsultas.remove(consulta);
	}

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);

		ClinicaDeConsultasAgil clinica = new ClinicaDeConsultasAgil();

		while (true) {

			System.out.println("Menu");
			System.out.println("1-Cadastrar paciente");
			System.out.println("2-Marcar consulta");
			System.out.println("3-Cancelar consulta");
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

					ClinicaDeConsultasAgil.Paciente novoPaciente = clinica.new Paciente();
					novoPaciente.nome = nome;
					novoPaciente.telefone = telefone;

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

					ClinicaDeConsultasAgil.Paciente pacienteSelecionado = clinica.listaPacientes.get(paciente);

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

									ClinicaDeConsultasAgil.Consulta novaConsulta = clinica.new Consulta(dia, hora,
											especialidade, pacienteSelecionado);

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
				for (int i = 0; i < ClinicaDeConsultasAgil.listaConsultas.size(); i++) {
					System.out.println(i + ". " + ClinicaDeConsultasAgil.listaConsultas.get(i));
				}

				System.out.println("Informe o número da consulta a ser cancelada:");
				int consulta = teclado.nextInt();

				if (consulta >= 0 && consulta < ClinicaDeConsultasAgil.listaConsultas.size()) {

					ClinicaDeConsultasAgil.Consulta consultaSelecionada = ClinicaDeConsultasAgil.listaConsultas
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
