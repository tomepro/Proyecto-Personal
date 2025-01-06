#pragma once
using namespace std;
using namespace System::Data;
using namespace System;
using namespace MySql::Data::MySqlClient;

ref class DB
{
private:
	String^ connectionString;
	MySqlConnection^ conn;
public:
	DB();
	DataTable^ getData();
	void AbrirConexion();
	void CerrarConexion();
	void Insertar(String^, String^, String^, String^, String^, String^);
	void Modificar(String^, String^, String^, String^ , String^ , String^ ,String^);
	void Eliminar(String^);
};

