package com.example.da1_qldh_yuii.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_qldh_yuii.Navigation;
import com.example.da1_qldh_yuii.R;
import com.example.da1_qldh_yuii.dao.BangGiaTheoSizeDAO;
import com.example.da1_qldh_yuii.dao.ChiTietHoaDonDAO;
import com.example.da1_qldh_yuii.dao.HoaDonDAO;
import com.example.da1_qldh_yuii.dao.KhachHangDAO;
import com.example.da1_qldh_yuii.dao.SanPhamDAO;
import com.example.da1_qldh_yuii.dao.TonKhoDAO;
import com.example.da1_qldh_yuii.dao.VanChuyenDAO;
import com.example.da1_qldh_yuii.model.BangGia;
import com.example.da1_qldh_yuii.model.ChiTietHoaDon;
import com.example.da1_qldh_yuii.model.HoaDon;
import com.example.da1_qldh_yuii.model.KhachHang;
import com.example.da1_qldh_yuii.model.SanPham;
import com.example.da1_qldh_yuii.model.TonKho;
import com.example.da1_qldh_yuii.model.VanChuyen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.viewholder> {
    HoaDonDAO hdDAO;
    ChiTietHoaDonDAO ctDAO;
    KhachHangDAO khDAO;
    BangGiaTheoSizeDAO bgDAO;
    VanChuyenDAO vcDAO;
    SanPhamDAO spDAO;
    TonKhoDAO tkDAO;
    private int initialMin;
    private int initialhours;
    final Context context;
    private final ArrayList<HoaDon> list;
    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public HoaDonAdapter(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
        hdDAO = new HoaDonDAO(context);
        khDAO = new KhachHangDAO(context);
        ctDAO = new ChiTietHoaDonDAO(context);
        bgDAO = new BangGiaTheoSizeDAO(context);
        spDAO = new SanPhamDAO(context);
        vcDAO = new VanChuyenDAO(context);
        tkDAO = new TonKhoDAO(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_qlhoadon,parent,false);
        viewholder vholder = new viewholder(view);
        vholder.setOnItemClickListener(vholder.listener);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {



        if (position >=0 && position < list.size()){
            HoaDon hd = list.get(position);
            KhachHang kh = khDAO.getID(hd.getMaKhachHang());
            holder.tvTenKhachHang.setText("Khách: "+kh.getTenKhachHang());
            holder.tvHD.setText("Mã: "+hd.getMaHoaDon());
            holder.tvNgayNhan.setText("Ngày nhận: "+hd.getNgayNhanHang());

            if (hd.getTrangThai() == 0){
                holder.tvTrangThai.setText("Chưa giao");
                holder.tvTrangThai.setTextColor(Color.RED);
                holder.tvTenKhachHang.setTextColor(Color.RED);
                holder.tvHD.setTextColor(Color.RED);
                holder.tvNgayNhan.setTextColor(Color.RED);
                holder.tvTongTien.setTextColor(Color.RED);
            }else if (hd.getTrangThai() == 1){
                holder.tvTrangThai.setText("Đã giao");
                holder.tvTrangThai.setTextColor(ContextCompat.getColor(context, R.color.blue));
                holder.tvTenKhachHang.setTextColor(ContextCompat.getColor(context, R.color.blue));
                holder.tvHD.setTextColor(ContextCompat.getColor(context, R.color.blue));
                holder.tvNgayNhan.setTextColor(ContextCompat.getColor(context, R.color.blue));
                holder.tvTongTien.setTextColor(ContextCompat.getColor(context, R.color.blue));
            }else if (hd.getTrangThai() == 2){
                holder.tvTrangThai.setText("Đang giao");
                holder.tvTrangThai.setTextColor(ContextCompat.getColor(context, R.color.green));
                holder.tvTenKhachHang.setTextColor(ContextCompat.getColor(context, R.color.green));
                holder.tvHD.setTextColor(ContextCompat.getColor(context, R.color.green));
                holder.tvNgayNhan.setTextColor(ContextCompat.getColor(context, R.color.green));
                holder.tvTongTien.setTextColor(ContextCompat.getColor(context, R.color.green));
//                if (hd.getGio()+2 == gioHienTai){
//                    hd.setTrangThai(1);
//                    hdDAO.update(hd);
//                    Toast.makeText(context, hd.getMaHoaDon()+ " Đã được cập nhật trạng thái", Toast.LENGTH_SHORT).show();
//                    hd.setNgayGiaoOk( new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
//                }
            }else {
                holder.tvTrangThai.setText("Hủy");
                holder.tvTenKhachHang.setTextColor(Color.GRAY);
                holder.tvHD.setTextColor(Color.GRAY);
                holder.tvNgayNhan.setTextColor(Color.GRAY);
                holder.tvTrangThai.setTextColor(Color.GRAY);
                holder.tvTongTien.setTextColor(Color.GRAY);
                TonKho tonKho = new TonKho(hd.getMaHoaDon());
                tkDAO.insert(tonKho);
            }
            List<String> ArrMasp = new ArrayList<>();
            List<Integer> ArrSL = new ArrayList<>();
            List<Double> ArrGia = new ArrayList<>();

            ArrayList<ChiTietHoaDon> listct = (ArrayList<ChiTietHoaDon>) ctDAO.getID(hd.getMaHoaDon());
            for (ChiTietHoaDon ct : listct){
                ArrMasp.add(ct.getMaSanPham());
                ArrSL.add(ct.getSoLuong());
            }

            double tongTien = 0;
            for (ChiTietHoaDon ct : listct){
                BangGia bg = bgDAO.getID(spDAO.getID(ct.getMaSanPham()).getMaBangGia());
                double giaban = bg.getGiaBan();
                ArrGia.add(giaban);
                int soluong = ct.getSoLuong();
                tongTien += (giaban*soluong);
            }
            VanChuyen vc = vcDAO.getID(hd.getMaVanChuyen());
            double phivc = vc.getGiaVanChuyen();

            tongTien = (tongTien + phivc) - tongTien * hd.getTienCoc() * 0.01;

            holder.tvTongTien.setText("Tổng hóa đơn : "+tongTien+" VNĐ");


            double finalTongTien = tongTien;
            holder.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    opendialog(hd,kh,vc,ArrMasp,ArrSL, finalTongTien,ArrGia);
                }
            });
        }
    }

    private void opendialog(HoaDon hd, KhachHang kh, VanChuyen vc, List<String> ArrMasp,List<Integer> ArrSL,double tongtien,List<Double> ArrGia) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_chitiethoadon,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView tvMaHoaDon = view.findViewById(R.id.tvMaHoaDon);
        TextView tvTrangThaiHD = view.findViewById(R.id.tvTrangThaiHD);
        TextView tvTenKhach = view.findViewById(R.id.tvTenKhach);
        TextView tvSDTKhach = view.findViewById(R.id.tvSDTKhach);
        TextView tvNgayNhanSP = view.findViewById(R.id.tvNgayNhanSP);
        TextView tvDiaChiKhach = view.findViewById(R.id.tvDiaChiKhach);
        TextView tvTenSanPham = view.findViewById(R.id.tvTenSanPham);
        TextView tvSoLuong = view.findViewById(R.id.tvSoLuong);
        TextView tvGia = view.findViewById(R.id.tvGia);
        TextView tvVanChuyen = view.findViewById(R.id.tvVanChuyen);
        TextView tvGiamGia = view.findViewById(R.id.tvGiamGia);
        TextView tvTienCoc = view.findViewById(R.id.tvTienCoc);
        TextView tvTongTien = view.findViewById(R.id.tvTongTien);
        TextView tvMoTa = view.findViewById(R.id.tvMoTa);
        TextView tvNgayTaoDon = view.findViewById(R.id.tvNgayTaoDon);
        TextView tvGio = view.findViewById(R.id.tvGio);
        TextView tvNgayGiaoHang = view.findViewById(R.id.tvNgayGiaoHang);
        TextView tvNgayGiaoHangOk = view.findViewById(R.id.tvNgayGiaoHangOk);
        Button btnGui = view.findViewById(R.id.btnGui);
        Button btnHuyHD = view.findViewById(R.id.btnHuyHD);
        Button btnTaoHD = view.findViewById(R.id.btnTaoHD);


        btnTaoHD.setVisibility(View.GONE);
        SharedPreferences pref = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        int level = pref.getInt("LEVEL", 1);

        tvMaHoaDon.setText("Mã : "+hd.getMaHoaDon());
        tvMaHoaDon.setTextColor(Color.RED);
        if (hd.getTrangThai() == 0 && level == 1){
            btnGui.setVisibility(View.GONE);
            tvTrangThaiHD.setText("Chưa giao");
            tvTrangThaiHD.setTextColor(Color.RED);
        } else if (hd.getTrangThai() == 0) {
            tvTrangThaiHD.setText("Chưa giao");
            tvTrangThaiHD.setTextColor(Color.RED);
        }else if (hd.getTrangThai() == 1) {
            tvTrangThaiHD.setText("Đã giao");
            btnGui.setVisibility(View.GONE);
            tvTrangThaiHD.setTextColor(ContextCompat.getColor(context, R.color.blue));
            btnHuyHD.setVisibility(View.GONE);
        }  else if (hd.getTrangThai() == 2 ) {
            tvTrangThaiHD.setText("Đang giao");
            tvTrangThaiHD.setTextColor(ContextCompat.getColor(context, R.color.green));
            btnHuyHD.setVisibility(View.GONE);
            btnGui.setVisibility(View.GONE);
        }else {
            btnGui.setVisibility(View.GONE);
            btnHuyHD.setVisibility(View.GONE);
            tvTrangThaiHD.setText("Hủy");
            tvTrangThaiHD.setTextColor(Color.GRAY);
        }

        List<String> listTensp = new ArrayList<>();
        for (String str : ArrMasp){
            SanPham sanPham = spDAO.getID(str);
            listTensp.add(sanPham.getTenSanPham());
        }

        tvTenKhach.setText("Tên khách hàng : "+kh.getTenKhachHang());
        tvSDTKhach.setText("Số điện thoại : "+kh.getSoDienThoai());
        tvNgayNhanSP.setText("Ngày nhận : "+hd.getNgayNhanHang());
        tvDiaChiKhach.setText("Địa chỉ nhận : "+kh.getDiaChi());
        tvTenSanPham.setText("Sản phẩm : "+ listTensp.toString());
        tvSoLuong.setText("Số lượng : "+ ArrSL.toString());
        tvGia.setText("Giá : "+ ArrGia.toString());
        tvVanChuyen.setText("Phí vận chuyển: "+vc.getGiaVanChuyen()+" VNĐ");
        tvGiamGia.setText("Giảm giá : 0%");
        tvTienCoc.setText("Tiền cọc : "+hd.getTienCoc()+"%");
        tvTongTien.setText("Tổng tiền : "+tongtien+" VNĐ");
        tvMoTa.setText("Mô tả : "+ hd.getGhiChu());
        tvNgayTaoDon.setText("Ngày tạo đơn : "+hd.getNgayTao());
        tvNgayGiaoHang.setText("Ngày giao hàng : "+hd.getNgayGiaoHang());
        tvNgayGiaoHangOk.setText("Ngày hoàn thành :"+hd.getNgayGiaoOk());
//        tvGio.setText(""+hd.getGio());
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        initialMin = calendar.get(Calendar.MINUTE);
        initialhours = calendar.get(Calendar.HOUR_OF_DAY);
        String gioHienTai = " -- "+initialhours+":"+initialMin;


        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hd.setTrangThai(2);
                hd.setGio(initialMin);
                hd.setNgayGiaoHang( new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date())+gioHienTai);
                hdDAO.update(hd);
                Toast.makeText(context, "Đơn hàng đang được giao", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                notifyDataSetChanged();
            }
        });

        btnHuyHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Cảnh báo!");
                builder1.setMessage("Bạn có chắc chắn muốn hủy đơn hàng này không?");
                builder1.setPositiveButton("Không",null);
                builder1.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hd.setTrangThai(3);
                        hdDAO.update(hd);
                        Toast.makeText(context, "Đơn hàng đã bị hủy", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                builder1.show();
            }
        });



        btnTaoHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (list.size() > 0){
            return list.size();
        }
        return 0;
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        private OnItemClickListener listener;
        TextView tvTenKhachHang,tvHD,tvNgayNhan,tvTrangThai,tvTongTien;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tvTongTien=itemView.findViewById(R.id.tvTongTien);
            tvTrangThai=itemView.findViewById(R.id.tvTrangThai);
            tvNgayNhan=itemView.findViewById(R.id.tvNgayNhan);
            tvHD=itemView.findViewById(R.id.tvHD);
            tvTenKhachHang=itemView.findViewById(R.id.tvTenKhachHang);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
        public void setOnItemClickListener(OnItemClickListener mlistener) {
            listener = mlistener;
        }
    }


}
