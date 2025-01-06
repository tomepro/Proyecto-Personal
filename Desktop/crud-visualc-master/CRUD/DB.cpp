#include "pch.h"
#include "DB.h"


using namespace System::Windows::Forms;
using namespace System::Data;
using namespace System::Drawing;

DB::DB()
{
	this->connectionString = "datasource=localhost; username=root; password=""; database=proyecto_personal;";
	this->conn = gcnew MySqlConnection(this->connectionString);
}




void DB::AbrirConexion()
{
	this->conn->Open();
}


void DB::CerrarConexion()
{
	this->conn->Close();
}



DataTable^ DB::getData()
{
	String^ sql = "select * from productos";
	MySqlCommand^ cursor = gcnew MySqlCommand(sql, this->conn);
	MySqlDataAdapter^ data = gcnew MySqlDataAdapter(cursor);
	DataTable^ tabla = gcnew DataTable();
	data->Fill(tabla);
	return tabla;
}

void DB::Insertar(String^ n, String^ d, String^ p, String^ s, String^ c, String^ i)
{
	String^ sql = "insert into productos(nombre, descripcion, precio, stock, categoria, imagen_url) values ('" + n + "','" + d + "', '" + p + "', '" + s + "','" + c + "','" + i + "')";
	//String^ sql = "insert into productos(nombre, descripcion, precio, stock, categoria, imagen_url) values ('"+ n + "', '" + d + "', " + Convert::ToString(p) + ", " + Convert::ToString(s)+ ", '" + c + "', '" + i + "')";

	MySqlCommand^ cursor = gcnew MySqlCommand(sql, this->conn);
	try
	{
		cursor->ExecuteNonQuery();
	}
	catch (Exception^ e)
	{
		using namespace System::Windows::Forms;
		using namespace System::Data;
		using namespace System::Drawing;
		MessageBox::Show(e->Message);
	}
}


void DB::Modificar(String^ n, String^ d, String^ p, String^ s, String^  c, String^  i, String^ ref)
{

	//String^ sql = "update  productos set Nombre = '" + n + "', Edad = '" + e + "', Carrera = '" + c + "' where Nombre = '" + ref + "'";
	String^ sql = "update productos set nombre = '" + n + "', descripcion = '" + d + "', precio = '" + p + "', stock = '" + s + "', categoria = '" + c + "', imagen_url = '" + i + "' where nombre = '" + ref + "'";

	MySqlCommand^ cursor = gcnew MySqlCommand(sql, this->conn);
	try
	{
		using namespace System::Windows::Forms;
		using namespace System::Data;
		using namespace System::Drawing;
		cursor->ExecuteNonQuery();

	}
	catch (Exception^ e)
	{
		using namespace System::Windows::Forms;
		using namespace System::Data;
		using namespace System::Drawing;
		MessageBox::Show(e->Message);
	}
}

void DB::Eliminar(String^ nombre) {
	String^ sql = "delete from productos where id = '" + nombre + "'";
	MySqlCommand^ cursor = gcnew MySqlCommand(sql, this->conn);
	try
	{
		using namespace System::Windows::Forms;
		using namespace System::Data;
		using namespace System::Drawing;
		cursor->ExecuteNonQuery();
		using namespace System::Windows::Forms;
		using namespace System::Data;
		using namespace System::Drawing;
		MessageBox::Show("Eliminado correctamente!");

	}
	catch (Exception^ e)
	{
		using namespace System::Windows::Forms;
		using namespace System::Data;
		using namespace System::Drawing;
		MessageBox::Show(e->Message);
	}
}




