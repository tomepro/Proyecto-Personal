#pragma once

#include "DB.h"
namespace CRUD {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	/// <summary>
	/// Resumen de Modificar
	/// </summary>
	public ref class Modificar : public System::Windows::Forms::Form
	{
	public:
		Modificar(void)
		{
			InitializeComponent();
			//
			//TODO: agregar código de constructor aquí
			//
		}

	protected:
		/// <summary>
		/// Limpiar los recursos que se estén usando.
		/// </summary>
		~Modificar()
		{
			if (components)
			{
				delete components;
			}
		}
	private: System::Windows::Forms::Label^ label1;
	protected:


	public: System::Windows::Forms::TextBox^ txt_nombre;
	private:





	private: System::Windows::Forms::Button^ btn_guardar;
	private: System::Windows::Forms::Button^ btn_cancelar;
	private: System::Windows::Forms::Label^ label2;
	public: System::Windows::Forms::TextBox^ txt_descripcion;
	private:
	private: System::Windows::Forms::Label^ label3;
	public:
	public: System::Windows::Forms::TextBox^ txt_precio;
	private:
	private: System::Windows::Forms::Label^ label4;
	public:
	public: System::Windows::Forms::TextBox^ txt_stock;
	private:
	private: System::Windows::Forms::Label^ label5;
	public:
	public: System::Windows::Forms::TextBox^ txt_categoria;
	private:
	private: System::Windows::Forms::Label^ label6;
	public:
	public: System::Windows::Forms::TextBox^ txt_imagen_url;
	private:

	private:
		/// <summary>
		/// Variable del diseñador necesaria.
		/// </summary>
		System::ComponentModel::Container ^components;

#pragma region Windows Form Designer generated code
		/// <summary>
		/// Método necesario para admitir el Diseñador. No se puede modificar
		/// el contenido de este método con el editor de código.
		/// </summary>
		void InitializeComponent(void)
		{
			this->label1 = (gcnew System::Windows::Forms::Label());
			this->txt_nombre = (gcnew System::Windows::Forms::TextBox());
			this->btn_guardar = (gcnew System::Windows::Forms::Button());
			this->btn_cancelar = (gcnew System::Windows::Forms::Button());
			this->label2 = (gcnew System::Windows::Forms::Label());
			this->txt_descripcion = (gcnew System::Windows::Forms::TextBox());
			this->label3 = (gcnew System::Windows::Forms::Label());
			this->txt_precio = (gcnew System::Windows::Forms::TextBox());
			this->label4 = (gcnew System::Windows::Forms::Label());
			this->txt_stock = (gcnew System::Windows::Forms::TextBox());
			this->label5 = (gcnew System::Windows::Forms::Label());
			this->txt_categoria = (gcnew System::Windows::Forms::TextBox());
			this->label6 = (gcnew System::Windows::Forms::Label());
			this->txt_imagen_url = (gcnew System::Windows::Forms::TextBox());
			this->SuspendLayout();
			// 
			// label1 - Nombre
			// 
			this->label1->AutoSize = true;
			this->label1->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->label1->ForeColor = System::Drawing::Color::White;
			this->label1->Location = System::Drawing::Point(115, 16);
			this->label1->Margin = System::Windows::Forms::Padding(4, 0, 4, 0);
			this->label1->Name = L"label1";
			this->label1->Size = System::Drawing::Size(68, 20);
			this->label1->TabIndex = 0;
			this->label1->Text = L"Nombre";
			// 
			// txt_nombre
			// 
			this->txt_nombre->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->txt_nombre->Location = System::Drawing::Point(212, 13);
			this->txt_nombre->Margin = System::Windows::Forms::Padding(4, 4, 4, 4);
			this->txt_nombre->Multiline = true;
			this->txt_nombre->Name = L"txt_nombre";
			this->txt_nombre->Size = System::Drawing::Size(184, 27);
			this->txt_nombre->TabIndex = 10;
			// 
			// btn_guardar
			// 
			this->btn_guardar->BackColor = System::Drawing::Color::FromArgb(static_cast<System::Int32>(static_cast<System::Byte>(59)), static_cast<System::Int32>(static_cast<System::Byte>(159)),
				static_cast<System::Int32>(static_cast<System::Byte>(59)));
			this->btn_guardar->FlatAppearance->BorderSize = 0;
			this->btn_guardar->FlatStyle = System::Windows::Forms::FlatStyle::Flat;
			this->btn_guardar->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 8.25F, System::Drawing::FontStyle::Bold, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->btn_guardar->ForeColor = System::Drawing::Color::White;
			this->btn_guardar->Location = System::Drawing::Point(119, 293);
			this->btn_guardar->Margin = System::Windows::Forms::Padding(4, 4, 4, 4);
			this->btn_guardar->Name = L"btn_guardar";
			this->btn_guardar->Size = System::Drawing::Size(128, 28);
			this->btn_guardar->TabIndex = 6;
			this->btn_guardar->Text = L"Guardar Cambios";
			this->btn_guardar->UseVisualStyleBackColor = false;
			this->btn_guardar->Click += gcnew System::EventHandler(this, &Modificar::btn_guardar_Click);
			// 
			// btn_cancelar
			// 
			this->btn_cancelar->BackColor = System::Drawing::Color::Red;
			this->btn_cancelar->FlatAppearance->BorderSize = 0;
			this->btn_cancelar->FlatStyle = System::Windows::Forms::FlatStyle::Flat;
			this->btn_cancelar->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 8.25F, System::Drawing::FontStyle::Bold, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->btn_cancelar->ForeColor = System::Drawing::Color::White;
			this->btn_cancelar->Location = System::Drawing::Point(297, 293);
			this->btn_cancelar->Margin = System::Windows::Forms::Padding(4, 4, 4, 4);
			this->btn_cancelar->Name = L"btn_cancelar";
			this->btn_cancelar->Size = System::Drawing::Size(100, 28);
			this->btn_cancelar->TabIndex = 7;
			this->btn_cancelar->Text = L"Cancelar";
			this->btn_cancelar->UseVisualStyleBackColor = false;
			this->btn_cancelar->Click += gcnew System::EventHandler(this, &Modificar::btn_cancelar_Click);
			// 
			// label2 - Descripcion
			// 
			this->label2->AutoSize = true;
			this->label2->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->label2->ForeColor = System::Drawing::Color::White;
			this->label2->Location = System::Drawing::Point(115, 54);
			this->label2->Margin = System::Windows::Forms::Padding(4, 0, 4, 0);
			this->label2->Name = L"label2";
			this->label2->Size = System::Drawing::Size(68, 20);
			this->label2->TabIndex = 11;
			this->label2->Text = L"Descripcion";
			// 
			// textBox1 - txt_descripcion
			// 
			this->txt_descripcion->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->txt_descripcion->Location = System::Drawing::Point(212, 51);
			this->txt_descripcion->Margin = System::Windows::Forms::Padding(4);
			this->txt_descripcion->Multiline = true;
			this->txt_descripcion->Name = L"txt_descripcion";
			this->txt_descripcion->Size = System::Drawing::Size(184, 27);
			this->txt_descripcion->TabIndex = 12;
			// 
			// label3
			// 
			this->label3->AutoSize = true;
			this->label3->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->label3->ForeColor = System::Drawing::Color::White;
			this->label3->Location = System::Drawing::Point(115, 89);
			this->label3->Margin = System::Windows::Forms::Padding(4, 0, 4, 0);
			this->label3->Name = L"label3";
			this->label3->Size = System::Drawing::Size(68, 20);
			this->label3->TabIndex = 13;
			this->label3->Text = L"Precio";
			// 
			// textBox2 - txt_precio
			// 
			this->txt_precio->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->txt_precio->Location = System::Drawing::Point(212, 89);
			this->txt_precio->Margin = System::Windows::Forms::Padding(4);
			this->txt_precio->Multiline = true;
			this->txt_precio->Name = L"txt_precio";
			this->txt_precio->Size = System::Drawing::Size(184, 27);
			this->txt_precio->TabIndex = 14;
			// 
			// label4
			// 
			this->label4->AutoSize = true;
			this->label4->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->label4->ForeColor = System::Drawing::Color::White;
			this->label4->Location = System::Drawing::Point(115, 132);
			this->label4->Margin = System::Windows::Forms::Padding(4, 0, 4, 0);
			this->label4->Name = L"label4";
			this->label4->Size = System::Drawing::Size(68, 20);
			this->label4->TabIndex = 15;
			this->label4->Text = L"Cantidad";
			// 
			// textBox3 - txt_stock
			// 
			this->txt_stock->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->txt_stock->Location = System::Drawing::Point(212, 129);
			this->txt_stock->Margin = System::Windows::Forms::Padding(4);
			this->txt_stock->Multiline = true;
			this->txt_stock->Name = L"txt_stock";
			this->txt_stock->Size = System::Drawing::Size(184, 27);
			this->txt_stock->TabIndex = 16;
			// 
			// label5 - txt_categoria
			// 
			this->label5->AutoSize = true;
			this->label5->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->label5->ForeColor = System::Drawing::Color::White;
			this->label5->Location = System::Drawing::Point(115, 178);
			this->label5->Margin = System::Windows::Forms::Padding(4, 0, 4, 0);
			this->label5->Name = L"label5";
			this->label5->Size = System::Drawing::Size(68, 20);
			this->label5->TabIndex = 17;
			this->label5->Text = L"Categoria";
			// 
			// textBox4
			// 
			this->txt_categoria->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->txt_categoria->Location = System::Drawing::Point(212, 171);
			this->txt_categoria->Margin = System::Windows::Forms::Padding(4);
			this->txt_categoria->Multiline = true;
			this->txt_categoria->Name = L"txt_categoria";
			this->txt_categoria->Size = System::Drawing::Size(184, 27);
			this->txt_categoria->TabIndex = 18;
			// 
			// label6
			// 
			this->label6->AutoSize = true;
			this->label6->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->label6->ForeColor = System::Drawing::Color::White;
			this->label6->Location = System::Drawing::Point(115, 216);
			this->label6->Margin = System::Windows::Forms::Padding(4, 0, 4, 0);
			this->label6->Name = L"label6";
			this->label6->Size = System::Drawing::Size(68, 20);
			this->label6->TabIndex = 19;
			this->label6->Text = L"Imagen URL";
			// 
			// textBox5 - txt_imagen_url
			// 
			this->txt_imagen_url->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 9.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->txt_imagen_url->Location = System::Drawing::Point(212, 209);
			this->txt_imagen_url->Margin = System::Windows::Forms::Padding(4);
			this->txt_imagen_url->Multiline = true;
			this->txt_imagen_url->Name = L"txt_imagen_url";
			this->txt_imagen_url->Size = System::Drawing::Size(184, 27);
			this->txt_imagen_url->TabIndex = 20;
			// 
			// Modificar
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(8, 16);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->BackColor = System::Drawing::Color::FromArgb(static_cast<System::Int32>(static_cast<System::Byte>(23)), static_cast<System::Int32>(static_cast<System::Byte>(35)),
				static_cast<System::Int32>(static_cast<System::Byte>(49)));
			this->ClientSize = System::Drawing::Size(541, 351);
			this->Controls->Add(this->txt_imagen_url);
			this->Controls->Add(this->label6);
			this->Controls->Add(this->txt_categoria);
			this->Controls->Add(this->label5);
			this->Controls->Add(this->txt_stock);
			this->Controls->Add(this->label4);
			this->Controls->Add(this->txt_precio);
			this->Controls->Add(this->label3);
			this->Controls->Add(this->txt_descripcion);
			this->Controls->Add(this->label2);
			this->Controls->Add(this->btn_cancelar);
			this->Controls->Add(this->btn_guardar);
			this->Controls->Add(this->txt_nombre);
			this->Controls->Add(this->label1);
			this->Margin = System::Windows::Forms::Padding(4, 4, 4, 4);
			this->Name = L"Modificar";
			this->StartPosition = System::Windows::Forms::FormStartPosition::CenterScreen;
			this->Text = L"Modificar";
			this->ResumeLayout(false);
			this->PerformLayout();

		}
#pragma endregion
	private: System::Void btn_guardar_Click(System::Object^ sender, System::EventArgs^ e) {
		MessageBox::Show("Usuario actualizado correctamente!");
		this->Close();

	}
	private: System::Void btn_cancelar_Click(System::Object^ sender, System::EventArgs^ e) {
		this->Close();
	}
};
}
